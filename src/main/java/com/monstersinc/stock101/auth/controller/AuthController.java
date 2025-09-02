package com.monstersinc.stock101.auth.controller;

import com.monstersinc.stock101.auth.model.dto.LoginRequestDto;
import com.monstersinc.stock101.auth.model.dto.LoginResponse;
import com.monstersinc.stock101.auth.model.service.AuthService;
import com.monstersinc.stock101.user.model.vo.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController{

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인" , description ="사용자의 이메일과 비밀번호로 로그인합니다." )
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequestDto){

        LoginResponse loginResponse = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if(loginResponse != null){

        return ResponseEntity.ok().body(loginResponse);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/check-id")
    public ResponseEntity<Void> checkId(@RequestParam String id){
        return ResponseEntity.ok().build();
    }
}
