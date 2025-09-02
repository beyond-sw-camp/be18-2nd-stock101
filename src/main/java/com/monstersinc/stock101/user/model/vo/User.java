package com.monstersinc.stock101.user.model.vo;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private String tierCode;
}
