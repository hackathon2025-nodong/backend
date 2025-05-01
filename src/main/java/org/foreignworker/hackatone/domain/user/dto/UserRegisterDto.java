package org.foreignworker.hackatone.domain.user.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String password;
    private String username;
    //private String emailVerificationCode;
}
