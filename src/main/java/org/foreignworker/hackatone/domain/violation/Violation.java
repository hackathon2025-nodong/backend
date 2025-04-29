package org.foreignworker.hackatone.domain.violation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;

@Entity
@Table(name="Violation")
@Getter
@Setter
public class Violation extends BaseEntity {
    public enum SeverityLevel {
        LOW, MEDIUM, HIGH
    }
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer violationId;

    @Column(name="code", nullable = false)
    private String code;

    @Column(name="code", nullable = false)
    private String description;

    @Column(name="code")
    private String legalReference;

    @Enumerated(EnumType.STRING)
    @Column(name="severity", nullable = false)
    private SeverityLevel severity;

    @Column(name="code", nullable = false)
    private String recommendation;
}
