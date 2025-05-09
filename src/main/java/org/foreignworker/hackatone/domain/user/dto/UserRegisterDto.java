package org.foreignworker.hackatone.domain.user.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.foreignworker.hackatone.domain.user.enums.Gender;

import java.util.Date;

@Data
public class UserRegisterDto {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;
    
    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    private String username;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String phoneNumber;

    @NotNull(message = "성별은 필수 입력값입니다.")
    private Gender gender;

    @NotNull(message = "생년월일은 필수 입력값입니다.")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private Date birthDate;

    @NotNull(message = "국가 ID는 필수 입력값입니다.")
    private Long countryId;
}
