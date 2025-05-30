package org.foreignworker.hackatone.domain.complianceCheck;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;
import org.foreignworker.hackatone.domain.document.Document;
import org.foreignworker.hackatone.domain.violation.Violation;

import java.util.List;

@Entity
@Table(name="ComplianceCheck")
@Getter
@Setter
public class ComplianceCheck extends BaseEntity {
    public enum ComplianceStatus{
        VIOLATION,
        COMPLIANCE
    }
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="documentId")
    private Document document;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ComplianceStatus status;

    @OneToMany(mappedBy = "complianceCheck", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name="violations")
    private List<Violation> violations;

}
