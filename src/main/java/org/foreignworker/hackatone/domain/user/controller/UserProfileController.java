package org.foreignworker.hackatone.domain.user.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.user.dto.UserProfileDto;
import org.foreignworker.hackatone.domain.user.dto.UserProfileUpdateDto;
import org.foreignworker.hackatone.domain.user.dto.UserWithdrawDto;
import org.foreignworker.hackatone.domain.user.security.CustomUserDetails;
import org.foreignworker.hackatone.domain.user.security.JwtTokenProvider;
import org.foreignworker.hackatone.domain.user.service.UserService;
import org.foreignworker.hackatone.global.service.S3Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@MultipartConfig
public class UserProfileController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final S3Service s3Service;

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

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdrawUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserWithdrawDto withdrawDto,
            HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        userService.withdrawUser(userDetails.getEmail(), withdrawDto, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileDto> updateProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file) throws IOException {
        
        // 기존 프로필 이미지가 있다면 삭제
        UserProfileDto currentProfile = userService.getUserProfile(userDetails.getEmail());
        if (currentProfile.getProfileImageUrl() != null) {
            s3Service.deleteFile(currentProfile.getProfileImageUrl());
        }

        // 새 이미지 업로드
        String imageUrl = s3Service.uploadFile(file);

        // 프로필 업데이트 - 기존 정보 유지하면서 이미지만 업데이트
        UserProfileUpdateDto updateDto = new UserProfileUpdateDto();
        updateDto.setUsername(currentProfile.getUsername());
        updateDto.setPhoneNumber(currentProfile.getPhoneNumber());
        updateDto.setGender(currentProfile.getGender());
        updateDto.setBirthDate(currentProfile.getBirthDate());
        updateDto.setCountryId(currentProfile.getCountryId());
        updateDto.setProfileImageUrl(imageUrl);
        
        return ResponseEntity.ok(userService.updateUserProfile(userDetails.getEmail(), updateDto));
    }
} 