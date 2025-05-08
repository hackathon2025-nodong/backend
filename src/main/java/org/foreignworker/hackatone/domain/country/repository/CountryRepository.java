package org.foreignworker.hackatone.domain.country.repository;

import org.foreignworker.hackatone.domain.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
} 