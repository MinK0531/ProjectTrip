package com.mink.projecttrip.country.repository;

import com.mink.projecttrip.country.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    public List<Country> findAllByOrderByCountryNameKoAsc();

}
