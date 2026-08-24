package com.mink.projecttrip.city;

import com.mink.projecttrip.city.dto.CityRequest;
import com.mink.projecttrip.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityRestController {

    private final CityService cityService;

    @PostMapping("/list")
    public List<String> getCityList(
            @RequestBody CityRequest request
    ) {

        return cityService.getCities(
                request.getCountryCode()
        );
    }

}
