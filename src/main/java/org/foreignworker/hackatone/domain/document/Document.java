package org.foreignworker.hackatone.domain.document;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;
import org.foreignworker.hackatone.domain.user.entity.User;

import java.util.Date;

@Entity
@Table(name = "document")
@Getter
@Setter
//문서 제외
public class Document extends BaseEntity {
    public enum DocumentType{
        Residence,
        PayStub,
        EmployContract
    }
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false)
    private DocumentType documentType;

    @Column(name = "originUrl", nullable = false)
    private String originUrl;

    @Column(name = "issueDate")
    private Date issueDate;

    @Column(name = "expiryDate")
    private Date expiryDate;

    @Column(name = "extractedData", nullable = false)
    private String extractedData;



}
