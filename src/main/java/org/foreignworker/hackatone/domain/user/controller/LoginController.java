package org.foreignworker.hackatone.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.user.dto.JwtAuthenticationResponse;
import org.foreignworker.hackatone.domain.user.dto.UserLoginDto;
import org.foreignworker.hackatone.domain.user.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080", allowCredentials = "true")
public class LoginController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto request) {
        try {
            // 1. 이메일과 비밀번호로 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // 2. 토큰 생성
            String accessToken = jwtTokenProvider.createAccessToken(request.getEmail());
            String refreshToken = jwtTokenProvider.createRefreshToken(request.getEmail());

            // 3. 응답 반환
            return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshToken));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}