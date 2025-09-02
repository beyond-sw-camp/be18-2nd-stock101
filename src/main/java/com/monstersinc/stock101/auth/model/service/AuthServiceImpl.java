package com.monstersinc.stock101.auth.model.service;

import com.monstersinc.stock101.auth.model.dto.LoginResponse;
import com.monstersinc.stock101.auth.model.mapper.AuthMapper;
import com.monstersinc.stock101.user.model.service.UserService;
import com.monstersinc.stock101.user.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    @Override
    public LoginResponse login(String email, String password) {
        User user = authMapper.selectUserByEmail(email);
        if(user == null){
            return null;
        }else{
            return LoginResponse.builder()
                    .accessToken("성공했다")
                    .build();
        }

    }
}
