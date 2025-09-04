package com.monstersinc.stock101.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60L * 15; // 15분
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60L * 60L * 24L; // 1일

    // TODO: 관리자, 매니저 권한 필요시 #auth-001
    //public String createAccessToken(Long userId, List<String> roles) {
    public String createAccessToken(Long userId) {
        Map<String, Object> claims = Map.of("userId", userId, "token_type", "access");
        return jwtUtil.createJwtToken(claims, ACCESS_TOKEN_EXPIRATION);
    }

    public String createRefreshToken(Long userId) {
        Map<String, Object> claims = Map.of("username", userId, "token_type", "refresh");
        String refreshToken = jwtUtil.createJwtToken(claims, REFRESH_TOKEN_EXPIRATION);

        // TODO: 리프레시 토큰 저장 레디스 연동 #auth-002.
        return refreshToken;
    }
}
