package org.foreignworker.hackatone.domain.analysis;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;
import org.foreignworker.hackatone.domain.document.Document;

@Entity
@Table
@Getter
@Setter
public class Analysis extends BaseEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer analysisId;
    //uid는 document에 uid 있는데 넣어야하나 ?

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="documentId")
    private Document document;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="isLegal")
    private boolean isLegal;
}

