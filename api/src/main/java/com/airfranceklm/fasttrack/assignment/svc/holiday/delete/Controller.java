package com.airfranceklm.fasttrack.assignment.svc.holiday.delete;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import com.airfranceklm.fasttrack.assignment.lib.jpaentities.QHoliday;
import com.airfranceklm.fasttrack.assignment.lib.repos.HolidayRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.airfranceklm.fasttrack.assignment.svc.holiday.delete.ConstraintChecker.assertHolidayIsAtLeastFiveWorkingDaysInFuture;

@RestController
@RequiredArgsConstructor
class Controller {
    private final @NonNull HolidayRepository holidayRepository;

    @DeleteMapping("/holidays")
    public void delete(@RequestBody @NonNull DeleteRequest request) {
        holidayRepository.delete(
            query -> query.where(QHoliday.holiday.holidayId.eq(request.holidayId)),
            this::checkConstraints
        );
    }

    public record DeleteRequest(
        @NonNull UUID holidayId
    ) {}

    private void checkConstraints(@NonNull Holiday holiday) {
        assertHolidayIsAtLeastFiveWorkingDaysInFuture(holiday);
    }
}
