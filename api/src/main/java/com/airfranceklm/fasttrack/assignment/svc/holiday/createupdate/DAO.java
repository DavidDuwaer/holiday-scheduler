package com.airfranceklm.fasttrack.assignment.svc.holiday.createupdate;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Employee;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.airfranceklm.fasttrack.assignment.lib.jpaentities.QEmployee.employee;
import static com.airfranceklm.fasttrack.assignment.lib.jpaentities.QHoliday.holiday;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
class DAO {
    private final @NonNull JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public @NonNull Employee getEmployee(@NonNull String employeeId) {
        val result = queryFactory
            .selectFrom(employee)
            .where(employee.employeeId.eq(employeeId))
            .fetchOne();
        if (isNull(result)) {
            throw new IllegalArgumentException(
                format("No employee found with ID \"%s\"", employeeId)
            );
        }
        return result;
    }

    public @NonNull Boolean holidaysExist(
        @Nullable String employeeId,
        @NonNull Instant betweenStart,
        @NonNull Instant beteenEnd
    ) {
        var query = queryFactory.selectFrom(holiday)
            .where(holiday.endOfHoliday.after(betweenStart))
            .where(holiday.startOfHoliday.before(beteenEnd));

        if (nonNull(employeeId)) {
            query = query.where(holiday.employee.employeeId.eq(employeeId));
        }

        return !query.fetch().isEmpty();
    }
}
