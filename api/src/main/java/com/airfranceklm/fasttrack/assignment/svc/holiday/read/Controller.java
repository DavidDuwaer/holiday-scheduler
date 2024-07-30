package com.airfranceklm.fasttrack.assignment.svc.holiday.read;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.HolidayStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class Controller {
    private final @NonNull DAO dao;

    @GetMapping("/holidays")
    public @NonNull List<HolidayDto> get() {
        return dao.getHolidays()
            .stream()
            .map(h -> new HolidayDto(
                h.getHolidayId(),
                h.getStartOfHoliday(),
                h.getEndOfHoliday(),
                h.getEmployee().getEmployeeId(),
                h.getStatus(),
                h.getLabel()
            ))
            .toList();
    }

    record HolidayDto(
        @NonNull UUID holidayId,
        @NonNull Instant startOfHoliday,
        @NonNull Instant endOfHoliday,
        @NonNull String employeeId,
        @NonNull HolidayStatus status,
        @NonNull String label
    ) {}
}
