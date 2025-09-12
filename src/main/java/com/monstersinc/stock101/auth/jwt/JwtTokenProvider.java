package com.monstersinc.stock101.auth.jwt;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60L * 15; // 15분
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60L * 60L * 24L; // 1일
    private final RedisTemplate<String, String> redisTemplate;
    private final UserDetailsService userDetailsService;

    public String createAccessToken(Long userId, List<String> roles) {
        Map<String, Object> claims = Map.of("userId", userId, "token_type", "access");
        return jwtUtil.createJwtToken(claims, ACCESS_TOKEN_EXPIRATION);
    }

    public String createRefreshToken(Long userId) {
        Map<String, Object> claims = Map.of("userId", userId, "token_type", "refresh");
        String refreshToken = jwtUtil.createJwtToken(claims, REFRESH_TOKEN_EXPIRATION);

        redisTemplate.opsForValue().set(
                "refresh" + userId, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);

        // 저장된 값을 다시 읽어서 확인
        String storedToken = redisTemplate.opsForValue().get("refresh" + userId);
        System.out.println("Redis에 저장된 리프레시 토큰: " + storedToken);

        return refreshToken;
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void addBlacklist(String accessToken) {
        String key = "blacklist:" + jwtUtil.getJti(accessToken);

        redisTemplate.opsForValue().set(key, accessToken, ACCESS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    public void deleteRefreshToken(String accessToken) {
        String userId = jwtUtil.getUserId(accessToken);

        redisTemplate.delete("refresh:" + userId);
    }

    public boolean isUsableAccessToken(String accessToken) {
        return accessToken != null
                && jwtUtil.validateToken(accessToken)
                && !isBlacklist(accessToken)
                && !isAccessToken(accessToken);
    }

    public Authentication createAuthentication(String accessToken) {
        String userId = jwtUtil.getUserId(accessToken);
        UserDetails userDetails= userDetailsService.loadUserByUsername(userId);

        return new UsernamePasswordAuthenticationToken(accessToken, null, Collections.emptyList());
    }

    private boolean isAccessToken(String accessToken) {
        return jwtUtil.getTokenType(accessToken).equals("access");
    }

    private boolean isBlacklist(String accessToken) {
        String key = "blacklist:" + jwtUtil.getJti(accessToken);
        return redisTemplate.hasKey(key);
    }

    private boolean isValidRefreshToken(String refreshToken) {
        String userId = jwtUtil.getUserId(refreshToken);
        String storedRefreshToken = redisTemplate.opsForValue().get("refresh:" + userId);

        return storedRefreshToken != null && storedRefreshToken.equals(refreshToken);
    }
}
