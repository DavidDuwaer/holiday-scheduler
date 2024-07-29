package com.airfranceklm.fasttrack.assignment.lib.jpaentities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED, force = true) // For Hibernate
@RequiredArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_id", nullable = false)
    private final @NonNull String employeeId;

    @Column(name = "name", nullable = false)
    private @NonNull String name;
}
