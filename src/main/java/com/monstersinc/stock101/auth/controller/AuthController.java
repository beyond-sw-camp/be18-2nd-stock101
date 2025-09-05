package com.monstersinc.stock101.auth.controller;

import com.monstersinc.stock101.auth.model.dto.LoginRequestDto;
import com.monstersinc.stock101.auth.model.dto.LoginResponse;
import com.monstersinc.stock101.auth.model.service.AuthService;
import com.monstersinc.stock101.auth.model.service.JwtCookieService;
import com.monstersinc.stock101.user.model.vo.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "로그인", description = "사용자의 이메일과 비밀번호로 로그인합니다.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequestDto) {

        //login에 필요한 정보를 가져온다.
        // 사용자 확인
        LoginResponse loginResponse = authService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword());

        // 사용자 확인후 이상없다면 토큰 발행.
        String refreshToken = authService.createRefreshToken(loginResponse.getUserId());
        ResponseCookie cookie = jwtCookieService.createRefreshTokenCookie(refreshToken, Duration.ofDays(1));
        HttpHeaders headers = jwtCookieService.createRefreshTokenCookieHeaders(cookie);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) {
        return ResponseEntity.ok().build();

    }

    @GetMapping("/auth/check-id")
    public ResponseEntity<Void> checkId(@RequestParam String id) {
        return ResponseEntity.ok().build();

    }

/*    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
    }*/
}
