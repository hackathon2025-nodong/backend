package org.foreignworker.hackatone.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.country.Country;
import org.foreignworker.hackatone.domain.country.repository.CountryRepository;
import org.foreignworker.hackatone.domain.user.enums.Role;
import org.foreignworker.hackatone.domain.user.dto.UserRegisterDto;
import org.foreignworker.hackatone.domain.user.dto.UserProfileDto;
import org.foreignworker.hackatone.domain.user.dto.UserProfileUpdateDto;
import org.foreignworker.hackatone.domain.user.dto.UserWithdrawDto;
import org.foreignworker.hackatone.domain.user.entity.User;
import org.foreignworker.hackatone.domain.user.repository.UserRepository;
import org.foreignworker.hackatone.domain.user.service.TokenBlacklistService;
import org.foreignworker.hackatone.global.error.ErrorCode;
import org.foreignworker.hackatone.global.error.exception.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public Long register(UserRegisterDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다."));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        user.setBirthDate(request.getBirthDate());
        user.setCountry(country);
        user.setRole(Role.USER);
        user.setLastLogin(Time.valueOf(LocalTime.now()));
        user.setAccountLocked(false);

        return userRepository.save(user).getUid();
    }

    // 프로필 조회
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("사용자를 찾을 수 없습니다.", ErrorCode.USER_NOT_FOUND));
        return UserProfileDto.fromEntity(user);
    }

    // 프로필 수정
    @Transactional
    public UserProfileDto updateUserProfile(String email, UserProfileUpdateDto updateDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("사용자를 찾을 수 없습니다.", ErrorCode.USER_NOT_FOUND));

        if (updateDto.getCountryId() != null) {
            Country country = countryRepository.findById(updateDto.getCountryId())
                    .orElseThrow(() -> new ApiException("국가 정보를 찾을 수 없습니다.", ErrorCode.COUNTRY_NOT_FOUND));
            user.setCountry(country);
        }

        user.setUsername(updateDto.getUsername());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        if (updateDto.getGender() != null) {
            user.setGender(updateDto.getGender());
        }
        if (updateDto.getBirthDate() != null) {
            user.setBirthDate(updateDto.getBirthDate());
        }
        if (updateDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(updateDto.getProfileImageUrl());
        }

        return UserProfileDto.fromEntity(userRepository.save(user));
    }

    // 회원 탈퇴
    @Transactional
    public void withdrawUser(String email, UserWithdrawDto withdrawDto, String token) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("사용자를 찾을 수 없습니다.", ErrorCode.USER_NOT_FOUND));

        // 비밀번호 확인
        if (!passwordEncoder.matches(withdrawDto.getPassword(), user.getPassword())) {
            throw new ApiException("비밀번호가 일치하지 않습니다.", ErrorCode.INVALID_CREDENTIAL);
        }

        // 토큰 블랙리스트에 추가
        if (token != null) {
            tokenBlacklistService.blacklist(token);
        }

        // 사용자 삭제
        userRepository.delete(user);
    }
} 