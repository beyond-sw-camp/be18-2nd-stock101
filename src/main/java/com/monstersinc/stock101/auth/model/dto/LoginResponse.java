package com.monstersinc.stock101.auth.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
@RequiredArgsConstructor
public class LoginResponse {
    private final String accessToken;

    private final Long userId;

    private final String type;

    // TODO: 관리자, 매니저 권한 필요시 #auth-001
    // private final List<String> roles;

    private final long issuedAt;

    private final long expiresAt;
}
