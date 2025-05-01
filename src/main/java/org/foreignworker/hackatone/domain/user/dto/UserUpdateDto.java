package org.foreignworker.hackatone.domain.user.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String newPassword;
    private String name;
    private String profileImgUrl;
}