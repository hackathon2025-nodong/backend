package org.foreignworker.hackatone.global.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.country.Country;
import org.foreignworker.hackatone.domain.country.repository.CountryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final CountryRepository countryRepository;

    @PostConstruct
    @Transactional
    public void init() {
        if (countryRepository.count() == 0) {
            List<Country> countries = Arrays.asList(
                    new Country("필리핀", "PHL"),
                    new Country("몽골", "MNG"),
                    new Country("스리랑카", "LKA"),
                    new Country("베트남", "VNM"),
                    new Country("태국", "THA"),
                    new Country("인도네시아", "IDN"),
                    new Country("우즈베키스탄", "UZB"),
                    new Country("파키스탄", "PAK"),
                    new Country("캄보디아", "KHM"),
                    new Country("중국", "CHN"),
                    new Country("방글라데시", "BGD"),
                    new Country("네팔", "NPL"),
                    new Country("키르기스스탄", "KGZ"),
                    new Country("미얀마", "MMR"),
                    new Country("동티모르", "TLS"),
                    new Country("라오스", "LAO")
            );
            
            countryRepository.saveAll(countries);
        }
    }
} 