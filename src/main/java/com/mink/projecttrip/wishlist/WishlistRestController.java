package com.mink.projecttrip.wishlist;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlist")
public class WishlistRestController {

    private final WishlistService wishlistService;

    @PostMapping("/create")
    public ApiResponse<Void> create(
            @RequestParam long countryId,
            @RequestParam(required = false) String cityName,
            @RequestParam String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String memo,
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if (wishlistService.createWishlist(
                userId,
                countryId,
                cityName,
                period,
                startDate,
                endDate,
                memo)) {
            return ApiResponse.success("위시리스트 등록 성공");
        } else {
            return ApiResponse.fail("위시리스트 등록 실패");

        }
    }
}

