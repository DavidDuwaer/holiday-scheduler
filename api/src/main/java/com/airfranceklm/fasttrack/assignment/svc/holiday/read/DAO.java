package com.airfranceklm.fasttrack.assignment.svc.holiday.read;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.airfranceklm.fasttrack.assignment.lib.jpaentities.QHoliday.holiday;

@Component
@RequiredArgsConstructor
class DAO {
    private final @NonNull JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public @NonNull List<Holiday> getHolidays() {
        return queryFactory
            .selectFrom(holiday)
            .fetch();
    }
}
