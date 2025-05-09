package org.foreignworker.hackatone.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.user.dto.UserRegisterDto;
import org.foreignworker.hackatone.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//@Controller+@ResponseBody
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody UserRegisterDto request) {
        Long userId = userService.register(request);
        return ResponseEntity.ok(userId);
    }
}
