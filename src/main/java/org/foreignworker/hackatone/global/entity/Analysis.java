package org.foreignworker.hackatone.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Analysis extends BaseEntity{

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

