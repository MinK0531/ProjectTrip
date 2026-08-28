package com.mink.projecttrip.home.service;

import com.mink.projecttrip.home.dto.HomeDetail;
import com.mink.projecttrip.post.service.PostService;
import com.mink.projecttrip.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostService postService;
    private final WishlistService wishlistService;

    public List<HomeDetail> getFeedList(Long userId) {

        List<HomeDetail> feedList = new ArrayList<>();

        postService.getFeedList(userId).forEach(post -> {
            feedList.add(
                    HomeDetail.builder()
                            .type("POST")
                            .data(post)
                            .createdAt(post.getCreatedAt())
                            .build()
            );
        });

        wishlistService.getFeedList(userId).forEach(wishlist -> {
            feedList.add(
                    HomeDetail.builder()
                            .type("WISHLIST")
                            .data(wishlist)
                            .createdAt(wishlist.getCreatedAt())
                            .build()
            );
        });

        feedList.sort(
                Comparator.comparing(
                        HomeDetail::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );

        return feedList;

    }
}
