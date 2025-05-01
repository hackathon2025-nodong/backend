package org.foreignworker.hackatone.domain.violation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;

@Entity
@Table(name="Violations")
@Getter
@Setter
public class Violation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer violationId;

    @Column(name="code", nullable = false)
    private String code;

    private String description;

    private String legalReference;

    @Enumerated(EnumType.STRING)
    @Column(name="severity", nullable = false)
    private SeverityLevel severity;

    public enum SeverityLevel {
        LOW, MEDIUM, HIGH
    }

    private String recommendation;
}
