package com.mink.projecttrip.country.repository;

import com.mink.projecttrip.country.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    public List<Country> findAllByOrderByCountryNameKoAsc();
    Optional<Country> findByCountryCode(String countryCode);


    List<Country> findByCountryNameKoContainingIgnoreCase(String countryNameKo);
}
