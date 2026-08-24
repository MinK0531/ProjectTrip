package com.mink.projecttrip.wishlist.service;

import com.mink.projecttrip.city.service.CityService;
import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.repository.CountryRepository;
import com.mink.projecttrip.wishlist.domain.Wishlist;
import com.mink.projecttrip.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CityService cityService;

    public boolean createWishlist(
            long userId,
            long countryId,
            String cityName,
            String period,
            String startDate,
            String endDate,
            String memo
    ) {
        double[] coordinate =
                cityService.getCoordinate(countryId, cityName);

        Wishlist wishlist = Wishlist.builder()
                .userId(userId)
                .countryId(countryId)
                .cityName(cityName)
                .period(period)
                .startDate(startDate == null || startDate.isBlank() ? null : LocalDate.parse(startDate))
                .endDate(endDate == null || endDate.isBlank() ? null : LocalDate.parse(endDate))
                .memo(memo)
                .latitude(coordinate[0])
                .longitude(coordinate[1])
                .build();

        try {
            wishlistRepository.save(wishlist);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }


}
