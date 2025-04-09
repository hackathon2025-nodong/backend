package org.foreignworker.hackatone.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Violation")
@Getter
@Setter
public class Violation extends BaseEntity{
    public enum SeverityLevel {
        LOW, MEDIUM, HIGH
    }
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer violationId;
    //doc id가 아니라 check id?
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
