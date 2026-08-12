package com.mink.projecttrip.country.service;

import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public List<Country> getCountryList(){
        return countryRepository.findAllByOrderByCountryNameKoAsc();
    }


}
