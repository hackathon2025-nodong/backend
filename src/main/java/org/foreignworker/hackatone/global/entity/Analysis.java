package org.foreignworker.hackatone.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Analysis extends BaseEntity{
    public enum AnalysisType{

    }//analysistype필요?
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer analysisId;
    //uid는 document에 uid 있는데 넣어야하나 ?

    @Column(name="analysisType", nullable = false)
    private AnalysisType analysisType;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="isLegal")
    private boolean isLegal;
}

