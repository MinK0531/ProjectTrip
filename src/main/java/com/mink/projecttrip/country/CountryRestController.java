package com.mink.projecttrip.country;

import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryRestController {

    private final CountryService countryService;
    @GetMapping("/list")
    public List<Country> getCountryList(){
        return countryService.getCountryList();
    }

}
