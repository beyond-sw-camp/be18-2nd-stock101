package com.monstersinc.stock101.auth.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@RequiredArgsConstructor
public class LoginResponse {

    private final String accessToken;
}
