package com.airfranceklm.fasttrack.assignment.svc.holiday.createupdate;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static java.time.Instant.now;

@Component
@RequiredArgsConstructor
class ConstraintChecker {
    private final @NonNull DAO dao;

    void assertThreeDaysApartFromOtherHolidaysOfEmployee(
        @NonNull Holiday holiday
    ) {
        val threeDays = Duration.ofDays(3);
        val threeDaysBefore = holiday.getStartOfHoliday().minus(threeDays);
        val threeDaysAfter = holiday.getEndOfHoliday().plus(threeDays);
        if (dao.holidaysExist(holiday.getEmployee().getEmployeeId(), threeDaysBefore, threeDaysAfter)) {
            throw new IllegalArgumentException(
                "Holidays must be three days apart from other holidays by the same employee"
            );
        }
    }

    void assertNotDoesntOverlapWithAnyOtherHoliday(
        @NonNull Holiday holiday
    ) {
        if (dao.holidaysExist(null, holiday.getStartOfHoliday(), holiday.getEndOfHoliday())) {
            throw new IllegalArgumentException(
                "Holidays may not overlap with any other holiday"
            );
        }
    }

    static void assertHolidayIsAtLeastFiveWorkingDaysInFuture(
        @NonNull Holiday holiday
    ) {
        val fiveDaysFromNow = now().plus(Duration.ofDays(5));
        if (holiday.getStartOfHoliday().isBefore(fiveDaysFromNow)) {
            throw new IllegalArgumentException(
                "Holidays must be at least five days in the future"
            );
        }
    }
}
