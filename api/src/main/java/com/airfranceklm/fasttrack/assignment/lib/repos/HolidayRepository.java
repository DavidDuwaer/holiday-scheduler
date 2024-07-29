package com.airfranceklm.fasttrack.assignment.lib.repos;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Employee;
import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import com.airfranceklm.fasttrack.assignment.lib.jpaentities.HolidayStatus;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.airfranceklm.fasttrack.assignment.lib.jpaentities.QHoliday.holiday;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class HolidayRepository {
    private final @NonNull JPAQueryFactory queryFactory;
    private final @NonNull EntityManager entityManager;

    @Transactional
    public @NonNull Holiday create(
        @NonNull UUID holidayId,
        @NonNull String label,
        @NonNull Employee employee,
        @NonNull Instant startOfHoliday,
        @NonNull Instant endOfHoliday,
        @NonNull HolidayStatus status,
        @Nullable Consumer<Holiday> doStuffBeforeSave
    ) {
        val subject = new Holiday(holidayId, label, employee, startOfHoliday, endOfHoliday, status);
        if (nonNull(doStuffBeforeSave)) {
            doStuffBeforeSave.accept(subject);
        }
        assertSatisfiesConstraints(subject);
        entityManager.persist(subject);
        return subject;
    }

    @Transactional
    public @NonNull Holiday update(
        @NonNull Consumer<Holiday> doWork,
        @NonNull Function<JPAQuery<Holiday>, JPAQuery<Holiday>> where
    ) {
        final var subject = find(where);
        doWork.accept(subject);
        assertSatisfiesConstraints(subject);
        entityManager.persist(subject);
        return subject;
    }

    @Transactional
    public void delete(
        @NonNull Function<JPAQuery<Holiday>, JPAQuery<Holiday>> where,
        @Nullable Consumer<Holiday> checkBeforeDelete
    ) {
        val subject = find(where);
        if (nonNull(checkBeforeDelete)) {
            checkBeforeDelete.accept(subject);
        }
        queryFactory.delete(holiday)
            .where(holiday.holidayId.eq(subject.getHolidayId()))
            .execute();
    }

    private @NonNull Holiday find(Function<JPAQuery<Holiday>, JPAQuery<Holiday>> where) {
        val baseQuery = queryFactory.selectFrom(holiday);
        val constrainedQuery = where.apply(baseQuery);
        val match = constrainedQuery.fetch();
        if (match.isEmpty()) {
            throw new IllegalArgumentException(
                "No row matched the filters you specified"
            );
        }
        if (match.size() > 1) {
            throw new IllegalArgumentException(
                "More than one row matched the filters you specified"
            );
        }
        return requireNonNull(match.getFirst());
    }

    private static void assertSatisfiesConstraints(@NonNull Holiday subject) {
        val employeeId = subject.getEmployee().getEmployeeId();
        if (!Pattern.compile("^klm[0-9]{6}$").matcher(employeeId).matches()) {
            throw new IllegalArgumentException(
                format("\"%s\" is not a valid employee id", employeeId)
            );
        }
    }
}
