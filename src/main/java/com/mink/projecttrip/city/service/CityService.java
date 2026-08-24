package com.mink.projecttrip.city.service;

import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CountryRepository countryRepository;
    private final RestTemplate restTemplate;

    @Value("${opencage.api-key}")
    private String openCageApiKey;


    public List<String> getCities(String countryCode) {

        Country country = countryRepository
                .findByCountryCode(countryCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 국가입니다."
                        )
                );

        String url = "https://countriesnow.space/api/v0.1/countries/cities";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "country",
                country.getCountryNameEn()
        );

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        Map.class
                );

        if (response.getBody() == null) {
            return Collections.emptyList();
        }

        Object data = response.getBody().get("data");

        if (!(data instanceof List<?> cities)) {
            return Collections.emptyList();
        }

        return cities.stream()
                .map(String::valueOf)
                .toList();
    }

}