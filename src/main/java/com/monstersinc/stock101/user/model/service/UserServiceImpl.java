package com.monstersinc.stock101.user.model.service;

import com.monstersinc.stock101.user.model.dto.UserRegisterRequestDto;
import com.monstersinc.stock101.user.model.mapper.UserMapper;
import com.monstersinc.stock101.user.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User registerUser(UserRegisterRequestDto userRegisterRequestDto) {

        userMapper.findByEmail(userRegisterRequestDto.getEmail()).ifPresent(user->{
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        });

        String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());

        User user = User.builder()
                .password(encodedPassword)
                .email(userRegisterRequestDto.getEmail())
                .name(userRegisterRequestDto.getName())
                .build();

        userMapper.insertUser(user);

        return user;
    }
}
