package com.monstersinc.stock101.auth.model.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class JwtCookieServiceImpl implements JwtCookieService {
    @Override
    public HttpHeaders createRefreshTokenCookieHeaders(ResponseCookie cookie) {
        return null;
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken, Duration duration) {
        return null;
    }
}
