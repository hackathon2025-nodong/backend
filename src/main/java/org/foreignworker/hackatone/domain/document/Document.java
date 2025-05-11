package org.foreignworker.hackatone.domain.document;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.foreignworker.hackatone.global.entity.BaseEntity;
import org.foreignworker.hackatone.domain.user.entity.User;

import java.util.Date;

@Entity
@Table(name = "document")
@Getter
@NoArgsConstructor
//문서 제외
public class Document extends BaseEntity {
    public enum DocumentType{
        Residence,
        PayStub,
        EmployContract
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false)
    private DocumentType documentType;

    @Column(name = "originUrl")
    private String originUrl;

    @Column(name = "issueDate")
    private Date issueDate;

    @Column(name = "expiryDate")
    private Date expiryDate;

    @Column(name = "extractedData")
    private String extractedData;

    private String s3Url;

    @Column(columnDefinition = "TEXT")
    private String analysis;

    @Builder
    public Document(User user, DocumentType documentType, String s3Url, String analysis, String extractedData, String originUrl) {
        this.user = user;
        this.documentType = documentType;
        this.s3Url = s3Url;
        this.analysis = analysis;
        this.extractedData = extractedData;
        this.originUrl = originUrl;
    }
}
