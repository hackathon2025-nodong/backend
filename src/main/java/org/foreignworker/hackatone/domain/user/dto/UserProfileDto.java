package org.foreignworker.hackatone.domain.user.dto;

import lombok.Data;
import org.foreignworker.hackatone.domain.user.entity.User;
import org.foreignworker.hackatone.domain.user.enums.Gender;

import java.util.Date;

@Data
public class UserProfileDto {
    private Long uid;
    private String email;
    private String username;
    private String phoneNumber;
    private Gender gender;
    private Date birthDate;
    private String profileImageUrl;
    private Long countryId;
    private String countryName;

    public static UserProfileDto fromEntity(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setUid(user.getUid());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        dto.setBirthDate(user.getBirthDate());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        dto.setCountryId(user.getCountry().getId());
        dto.setCountryName(user.getCountry().getName());
        return dto;
    }
} 