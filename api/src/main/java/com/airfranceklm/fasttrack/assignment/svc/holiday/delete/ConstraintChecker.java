package com.airfranceklm.fasttrack.assignment.svc.holiday.delete;

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
