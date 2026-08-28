package com.mink.projecttrip.home.dto;


import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.wishlist.dto.WishlistDetail;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HomeDetail {

    private LocalDateTime createdAt;
    private Object data;
    private String type;
}
