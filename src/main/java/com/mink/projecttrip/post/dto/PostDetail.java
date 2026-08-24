package com.mink.projecttrip.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDetail {
    private long id;
    private long userId;
    private long countryId;

    private String nickName;
    private String countryName;
    private String cityName;
    private String contents;
    private String atmosphere;
    private String placeName;
    private String musicUrl;
    private double latitude;
    private double longitude;
    private LocalDateTime createdAt;
    private List<PostImageDetail> imageList;

    private int likeCount;
    private boolean isLike;


}
