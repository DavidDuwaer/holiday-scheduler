package com.airfranceklm.fasttrack.assignment.lib.jpaentities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED, force = true) // For Hibernate
@RequiredArgsConstructor
@Table(name = "holidays")
public class Holiday {
    @Id
    @Column(name = "holiday_id", nullable = false)
    private final @NonNull UUID holidayId;

    @Column(name = "created_at", nullable = false)
    private @NonNull Instant createdAt;

    @Column(name = "label", nullable = false)
    private @NonNull String label;

    @ManyToOne(
        targetEntity = Employee.class,
        fetch = LAZY,
        cascade = {}
    )
    @JoinColumn(name = "employee_id", nullable = false)
    private @NonNull Employee employee;

    @Column(name = "start_of_holiday", nullable = false)
    private @NonNull Instant startOfHoliday;

    @Column(name = "end_of_holiday", nullable = false)
    private @NonNull Instant endOfHoliday;

    @Column(name = "status", nullable = false)
    private final @NonNull HolidayStatus status;
}
