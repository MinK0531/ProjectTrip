package com.mink.projecttrip.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostMapPoint {

    private long postId;
    private double latitude;
    private double longitude;
    private String cityName;
    private String countryName;
}
