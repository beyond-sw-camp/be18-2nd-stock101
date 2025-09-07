package com.monstersinc.stock101.auth.controller;

import com.monstersinc.stock101.auth.model.dto.LoginRequestDto;
import com.monstersinc.stock101.auth.model.dto.LoginResponse;
import com.monstersinc.stock101.auth.model.service.AuthService;
import com.monstersinc.stock101.auth.model.service.JwtCookieService;
import com.monstersinc.stock101.user.model.dto.UserRegisterRequestDto;
import com.monstersinc.stock101.user.model.vo.User;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtCookieService jwtCookieService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {

        // ogin에 필요한 정보를 가져온다.
        // 사용자 확인
        LoginResponse loginResponse = authService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword());

        // 사용자 확인 후 이상없다면 토큰 발행.
        String refreshToken = authService.createRefreshToken(loginResponse.getUserId());
        ResponseCookie cookie = jwtCookieService.createRefreshTokenCookie(refreshToken, Duration.ofDays(1));
        HttpHeaders headers = jwtCookieService.createRefreshTokenCookieHeaders(cookie);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(hidden = true) @RequestHeader("Authorization") String bearerToken) {

        authService.logout(bearerToken);

        ResponseCookie cookie = jwtCookieService.deleteRefreshTokenCookie();
        HttpHeaders headers = jwtCookieService.createRefreshTokenCookieHeaders(cookie);

        return ResponseEntity
                .noContent()
                .headers(headers)
                .build();
    }

    // TODO : 자동 로그인 기능 구현. #18 #auth
}
