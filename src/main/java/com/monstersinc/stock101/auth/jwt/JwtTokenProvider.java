package com.monstersinc.stock101.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60L * 15; // 15분
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60L * 60L * 24L; // 1일

    public String createAccessToken(Long userId, List<String> roles) {
    Map<String, Object> claims = Map.of("userId", userId, "token_type", "access");
        return jwtUtil.createJwtToken(claims, ACCESS_TOKEN_EXPIRATION);
    }

    public String createRefreshToken(Long userId) {
        Map<String, Object> claims = Map.of("username", userId, "token_type", "refresh");
        String refreshToken = jwtUtil.createJwtToken(claims, REFRESH_TOKEN_EXPIRATION);

        // TODO : 로그인/ 로그아웃 레디스 연동 기능 #auth #16
        return refreshToken;
    }

    public String resolveToken(String bearerToken) {
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void addBlacklist(String accessToken) {
        // TODO : 로그인/ 로그아웃 레디스 연동 기능 #auth #16
        //String eky = "blacklist" + jwtUtil.getJti(accessToken);
    }

    public void deleteRefreshToken(String accessToken) {
        // TODO : 로그인/ 로그아웃 레디스 연동 기능 #auth #16
    }

    public boolean isUsableAccessToken(String token) {
        // TODO : 로그인/ 로그아웃 레디스 연동 기능 #auth #16
        return true;
    }

    public Authentication createAuthentication(String token) {
        // TODO : 로그인/ 로그아웃 레디스 연동 기능 #auth #16
        // UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
    }
}
