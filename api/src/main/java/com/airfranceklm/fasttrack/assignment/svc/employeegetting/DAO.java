package com.airfranceklm.fasttrack.assignment.svc.employeegetting;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Employee;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.airfranceklm.fasttrack.assignment.lib.jpaentities.QEmployee.employee;

@Component
@RequiredArgsConstructor
class DAO {
    private final @NonNull JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public @NonNull List<Employee> getEmployees() {
        return queryFactory
            .selectFrom(employee)
            .fetch();
    }
}
