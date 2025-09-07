package com.monstersinc.stock101.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// 유저 회원가입시 이메일, 이름, 비밀 번호가 필요합니다.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    // TODO : 비밀번호 조합 확인하는 로직 구현 필요 #auth-003
    @NotBlank(message = "비밀번호는 필 수 입력 값입니다.")
    private String password;

}
