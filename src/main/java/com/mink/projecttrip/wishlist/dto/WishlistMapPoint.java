package com.mink.projecttrip.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WishlistMapPoint {

    private long wishlistId;
    private double latitude;
    private double longitude;
    private String cityName;
    private String countryName;
    private String countryCode;
}
