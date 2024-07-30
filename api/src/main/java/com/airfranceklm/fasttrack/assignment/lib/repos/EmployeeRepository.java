package com.airfranceklm.fasttrack.assignment.lib.repos;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Employee;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.airfranceklm.fasttrack.assignment.lib.jpaentities.QEmployee.employee;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class EmployeeRepository {
    private final @NonNull JPAQueryFactory queryFactory;
    private final @NonNull EntityManager entityManager;

    @Transactional
    public @NonNull Employee create(
        @NonNull String employeeId,
        @NonNull String name,
        @Nullable Consumer<Employee> doStuffBeforeSave
    ) {
        val subject = new Employee(employeeId, name);
        if (nonNull(doStuffBeforeSave)) {
            doStuffBeforeSave.accept(subject);
        }
        assertSatisfiesConstraints(subject);
        entityManager.persist(subject);
        return subject;
    }

    @Transactional
    public @NonNull Employee update(
        @NonNull Consumer<Employee> doWork,
        @NonNull Function<JPAQuery<Employee>, JPAQuery<Employee>> where
    ) {
        final var subject = find(where);
        doWork.accept(subject);
        assertSatisfiesConstraints(subject);
        entityManager.persist(subject);
        return subject;
    }

    @Transactional
    public void delete(
        @NonNull Function<JPAQuery<Employee>, JPAQuery<Employee>> where,
        @Nullable Consumer<Employee> checkBeforeDelete
    ) {
        val subject = find(where);
        if (nonNull(checkBeforeDelete)) {
            checkBeforeDelete.accept(subject);
        }
        queryFactory.delete(employee)
            .where(employee.employeeId.eq(subject.getEmployeeId()))
            .execute();
    }

    private @NonNull Employee find(Function<JPAQuery<Employee>, JPAQuery<Employee>> where) {
        val baseQuery = queryFactory.selectFrom(employee);
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

    private static void assertSatisfiesConstraints(@NonNull Employee subject) {
        val employeeId = subject.getEmployeeId();
        if (!Pattern.compile("^klm[0-9]{6}$").matcher(employeeId).matches()) {
            throw new IllegalArgumentException(
                format("\"%s\" is not a valid employee id", employeeId)
            );
        }
    }
}
