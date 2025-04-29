package org.foreignworker.hackatone.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.foreignworker.hackatone.domain.country.Country;
import org.foreignworker.hackatone.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User extends BaseEntity {
    public enum Gender{
        MALE, FEMALE
    }
    @Id
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId") // FK
    private Country country;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    @Column(name="role",nullable = false)
    @ColumnDefault("'user'")
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birthDate", nullable = false)
    private Date birthDate;

    @Column(name = "lastLogin", nullable = false)
    private Time lastLogin;


}
