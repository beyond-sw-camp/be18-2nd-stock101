package com.monstersinc.stock101.user.controller;

import com.monstersinc.stock101.common.model.dto.BaseResponseDto;
import com.monstersinc.stock101.user.model.dto.UserRegisterRequestDto;
import com.monstersinc.stock101.user.model.service.UserService;
import com.monstersinc.stock101.user.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 사용자 회원가입
    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto<User>> registerUser(@RequestBody UserRegisterRequestDto userRequestDto) {

        User registeredUser = userService.registerUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseDto<>(HttpStatus.CREATED, registeredUser));
    }

    //
//    @GetMapping("/me")
//    public ResponseEntity<> getMe(){
//        return ResponseEntity.ok().build();
//    }
//
//
//    @PatchMapping("/me")
//    public ResponseEntity<Void> updateMe(){
//        return ResponseEntity.ok().build();
//
//    }


    // @TODO 2주뒤에 삭제 => 정보 삭제 취소하는 경우  => 스프링 스케줄링 기능 사용해야함. user-001
}
