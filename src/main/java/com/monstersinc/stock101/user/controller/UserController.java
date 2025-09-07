package com.monstersinc.stock101.user.controller;

import com.monstersinc.stock101.user.model.service.UserService;
import com.monstersinc.stock101.user.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<Void> getMe(){
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/me")
    public ResponseEntity<Void> updateMe(){
        return ResponseEntity.ok().build();

    }

    // 바로 삭제 => 낙장 불입인 경우
    // @TODO 2주뒤에 삭제 => 정보 삭제 취소하는 경우  => 스프링 스케줄링 기능 사용해야함. user-001
}
