package com.monstersinc.stock101.user.model.mapper;

import com.monstersinc.stock101.user.model.vo.User;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    // 사용자 정보 저장.
    void insertUser(User user);

    // 유저 조회
    Optional<User> findByEmail(@NotBlank(message = "이메일은 필수 입력 값입니다.") String email);
}
