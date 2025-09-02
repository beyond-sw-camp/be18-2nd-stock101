package com.monstersinc.stock101.user.controller;

import com.monstersinc.stock101.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-service")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
