package org.foreignworker.hackatone.domain.country;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.foreignworker.hackatone.global.entity.BaseEntity;

@Entity
@Table(name = "Countries")
@Getter
@NoArgsConstructor
public class Country extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String code;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
