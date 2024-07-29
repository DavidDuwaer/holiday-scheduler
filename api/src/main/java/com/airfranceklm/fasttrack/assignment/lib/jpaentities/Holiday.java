package com.airfranceklm.fasttrack.assignment.lib.jpaentities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED, force = true) // For Hibernate
@RequiredArgsConstructor
@Table(name = "holidays")
public class Holiday {
    @Id
    @Type(type = "pg-uuid")
    private final @NonNull UUID uuid;

    @Column(name = "label", nullable = false)
    private final @NonNull String label;

    @Column(name = "start", nullable = false)
    private @NonNull Instant start;

    @Column(name = "end", nullable = false)
    private @NonNull Instant end;
}
