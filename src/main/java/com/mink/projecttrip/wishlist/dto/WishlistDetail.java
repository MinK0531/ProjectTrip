package com.mink.projecttrip.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WishlistDetail {

    private long id;

    private long userId;
    private String nickName;
    private long countryId;
    private String countryName;

    private String cityName;
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;

    private double latitude;
    private double longitude;

    private LocalDateTime createdAt;
}
