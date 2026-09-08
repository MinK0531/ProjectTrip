package com.mink.projecttrip.mypage;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.country.service.CountryService;
import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.post.dto.PostMapPoint;
import com.mink.projecttrip.post.dto.PostTicketDetail;
import com.mink.projecttrip.post.service.PostService;
import com.mink.projecttrip.wishlist.dto.WishlistMapPoint;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageRestController {

    private final PostService postService;
    private final WishlistService wishlistService;
    private final CountryService countryService;

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
    @GetMapping("/post/tickets")
    public ApiResponse<List<PostTicketDetail>> getCountryTickets(
            @RequestParam String countryCode,
            HttpSession session
    ) {
        if (session == null ||
                session.getAttribute("userId") == null) {

            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        List<PostTicketDetail> data = postService.getCountryPostList(userId, countryCode);

        return ApiResponse.success("나라별 티켓 조회 성공", data);
    }
    @GetMapping("/{friendId}/post/tickets")
    public ApiResponse<List<PostTicketDetail>> getFriendCountryTickets(
            @PathVariable Long friendId,
            @RequestParam String countryCode
    ) {

        List<PostTicketDetail> data = postService.getCountryPostList(friendId, countryCode);

        return ApiResponse.success(
                "친구 나라별 티켓 조회 성공",
                data
        );
    }

}