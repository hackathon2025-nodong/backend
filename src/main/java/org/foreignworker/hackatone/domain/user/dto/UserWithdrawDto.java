package org.foreignworker.hackatone.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserWithdrawDto {
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    
    private String withdrawReason;  // 탈퇴 사유 (선택)
} 