package org.foreignworker.hackatone.domain.user.dto;

import lombok.Data;

@Data
public class UserLoginDto {

    private String email;
    private String password;
}
