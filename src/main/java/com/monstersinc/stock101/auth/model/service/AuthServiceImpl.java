package com.monstersinc.stock101.auth.model.service;

import com.monstersinc.stock101.auth.jwt.JwtTokenProvider;
import com.monstersinc.stock101.auth.jwt.JwtUtil;
import com.monstersinc.stock101.auth.model.dto.LoginResponse;
import com.monstersinc.stock101.auth.model.mapper.AuthMapper;
import com.monstersinc.stock101.user.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(String email, String password) {

        User user = authMapper.selectUserByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("아이디가 없거나 비밀번호가 잘못됐습니다.");
        }

        return createLoginResponse(user);
    }

    private LoginResponse createLoginResponse(User user) {
        // TODO: 관리자, 매니저 권한 필요시 #auth-001
        // List<String> authorities = new ArrayList<>();
        //user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        // AccessToken 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .type("Bearer")
                .issuedAt(jwtUtil.getIssuedAt(accessToken))
                .expiresAt(jwtUtil.getExpiration(accessToken))
                .build();
    }

    @Override
    public String createRefreshToken(Long userId) {

        return jwtTokenProvider.createRefreshToken(userId);

    }
}
