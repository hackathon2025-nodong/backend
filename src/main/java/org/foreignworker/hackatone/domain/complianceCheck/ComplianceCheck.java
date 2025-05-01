package org.foreignworker.hackatone.domain.complianceCheck;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;
import org.foreignworker.hackatone.domain.document.Document;

@Entity
@Table(name="ComplianceCheck")
@Getter
@Setter
public class ComplianceCheck extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="documentId")
    private Document document;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ComplianceStatus status;

    public enum ComplianceStatus{
        VIOLATION,
        COMPLIANCE
    }
}
