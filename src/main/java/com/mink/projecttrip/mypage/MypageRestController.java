package com.mink.projecttrip.mypage;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.post.dto.PostMapPoint;
import com.mink.projecttrip.post.service.PostService;
import com.mink.projecttrip.wishlist.dto.WishlistMapPoint;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageRestController {

    private final PostService postService;
    private final WishlistService wishlistService;
    @GetMapping("/post/point")
    public ApiResponse<List<PostMapPoint>> getMapPostPoint(
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        return ApiResponse.success(
                "내 게시물 좌표 조회 성공",
                postService.getMyPostPoints(userId)
        );
    }
    @GetMapping("/wishlist/point")
    public ApiResponse<List<WishlistMapPoint>> getMapWishlistPoint(
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        return ApiResponse.success(
                "내 게시물 좌표 조회 성공",
                wishlistService.getMyWishlistPoint(userId)
        );
    }
}
