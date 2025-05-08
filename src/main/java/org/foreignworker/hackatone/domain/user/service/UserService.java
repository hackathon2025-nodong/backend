package org.foreignworker.hackatone.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.country.Country;
import org.foreignworker.hackatone.domain.country.repository.CountryRepository;
import org.foreignworker.hackatone.domain.user.Role;
import org.foreignworker.hackatone.domain.user.dto.UserRegisterDto;
import org.foreignworker.hackatone.domain.user.entity.User;
import org.foreignworker.hackatone.domain.user.repository.UserRepository;
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
} 