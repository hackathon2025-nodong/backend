package org.foreignworker.hackatone.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.user.dto.UserProfileDto;
import org.foreignworker.hackatone.domain.user.dto.UserProfileUpdateDto;
import org.foreignworker.hackatone.domain.user.dto.UserWithdrawDto;
import org.foreignworker.hackatone.domain.user.security.CustomUserDetails;
import org.foreignworker.hackatone.domain.user.security.JwtTokenProvider;
import org.foreignworker.hackatone.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserProfileDto profile = userService.getUserProfile(userDetails.getEmail());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserProfileUpdateDto updateDto) {
        UserProfileDto updatedProfile = userService.updateUserProfile(userDetails.getEmail(), updateDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserWithdrawDto withdrawDto,
            HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        userService.withdrawUser(userDetails.getEmail(), withdrawDto, token);
        return ResponseEntity.ok().build();
    }
} 