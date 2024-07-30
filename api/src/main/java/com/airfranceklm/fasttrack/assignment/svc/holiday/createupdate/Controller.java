package com.airfranceklm.fasttrack.assignment.svc.holiday.createupdate;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import com.airfranceklm.fasttrack.assignment.lib.jpaentities.HolidayStatus;
import com.airfranceklm.fasttrack.assignment.lib.jpaentities.QHoliday;
import com.airfranceklm.fasttrack.assignment.lib.repos.HolidayRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

import static com.airfranceklm.fasttrack.assignment.svc.holiday.createupdate.ConstraintChecker.assertHolidayIsAtLeastFiveWorkingDaysInFuture;
import static java.time.Instant.now;

@RestController
@RequiredArgsConstructor
class Controller {
    private final @NonNull HolidayRepository holidayRepository;
    private final @NonNull DAO dao;
    private final @NonNull ConstraintChecker constraintChecker;

    @PostMapping("/holidays")
    public void create(@RequestBody @NonNull CreateRequest request) {
        holidayRepository.create(
            request.holidayId,
            now(),
            request.label,
            dao.getEmployee(request.employeeId),
            request.startOfHoliday,
            request.endOfHoliday,
            request.status,
            this::checkConstraints
        );
    }

    @PutMapping("/holidays")
    public void update(@RequestBody @NonNull CreateRequest request) {
        holidayRepository.update(
            holiday -> {
                holiday.setEmployee(
                    dao.getEmployee(request.employeeId)
                );
                holiday.setLabel(request.label);
                holiday.setStartOfHoliday(request.startOfHoliday);
                holiday.setEndOfHoliday(request.endOfHoliday);
                checkConstraints(holiday);
            },
            query -> query.where(QHoliday.holiday.holidayId.eq(request.holidayId))
        );
    }

    public record CreateRequest(
        @NonNull UUID holidayId,
        @NonNull String label,
        @NonNull String employeeId,
        @NonNull Instant startOfHoliday,
        @NonNull Instant endOfHoliday,
        @NonNull HolidayStatus status
    ) {}

    private void checkConstraints(@NonNull Holiday holiday) {
        constraintChecker.assertThreeDaysApartFromOtherHolidaysOfEmployee(holiday);
        constraintChecker.assertNotDoesntOverlapWithAnyOtherHoliday(holiday);
        assertHolidayIsAtLeastFiveWorkingDaysInFuture(holiday);
    }
}
