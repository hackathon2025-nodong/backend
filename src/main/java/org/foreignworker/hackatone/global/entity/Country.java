package org.foreignworker.hackatone.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Country")
@Getter
@Setter
public class Country extends BaseEntity{
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    @Column(nullable = false, unique = true)
    private String countryName;

    @Column(nullable = false)
    private String language;


}
