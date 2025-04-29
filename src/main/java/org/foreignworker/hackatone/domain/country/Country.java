package org.foreignworker.hackatone.domain.country;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.global.entity.BaseEntity;

@Entity
@Table(name = "Country")
@Getter
@Setter
public class Country extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    @Column(nullable = false, unique = true)
    private String countryName;

    @Column(nullable = false)
    private String language;


}
