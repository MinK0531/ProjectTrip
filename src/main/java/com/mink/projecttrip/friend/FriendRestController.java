package com.mink.projecttrip.friend;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.friend.domain.Friend;
import com.mink.projecttrip.friend.domain.FriendRequest;
import com.mink.projecttrip.friend.service.FriendService;
import com.mink.projecttrip.post.dto.PostMapPoint;
import com.mink.projecttrip.post.service.PostService;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.domain.UserProfile;
import com.mink.projecttrip.wishlist.dto.WishlistMapPoint;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendRestController {

    private final FriendService friendService;
    private final WishlistService wishlistService;
    private final PostService postService;


    @PostMapping("/request")
    public ApiResponse<Void> request(
            @RequestParam long toUserId,
            HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (long) session.getAttribute("userId");

        if(friendService.request(userId, toUserId)){
            return ApiResponse.success("친구 요청 성공", null);
        }else {
            return ApiResponse.fail("친구 요청 실패", null);
        }
    }

    @PostMapping("/accept")
    public ApiResponse<Void> accept(
            @RequestParam long fromUserId,
            HttpSession session){
        if(session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (long) session.getAttribute("userId");

        if(friendService.accept(fromUserId, userId)){
            return ApiResponse.success("친구 요청 수락 성공", null);
        }else{
            return  ApiResponse.fail("친구 요청 수락 실패",null);
        }

    }

    @PostMapping("/reject")
    public ApiResponse<Void> reject(
            @RequestParam long fromUserId,
            HttpSession session){
        if(session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (long) session.getAttribute("userId");
        if(friendService.reject(fromUserId, userId)){
            return ApiResponse.success("친구 거절 성공", null);
        }else {
            return ApiResponse.fail("친구 거절 실패", null);
        }
    }

    @GetMapping("/{friendId}/post/point")
    public ApiResponse<List<PostMapPoint>> getFriendPostPoint(
            @PathVariable Long friendId,
            HttpSession session
    ) {

        if (session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (long) session.getAttribute("userId");

        if (!friendService.isFriend(userId, friendId)) {
            return ApiResponse.fail("친구만 조회할 수 있습니다.");
        }

        return ApiResponse.success(
                "친구 게시물 좌표 조회 성공", postService.getMyPostPoints(friendId)
        );
    }


    @GetMapping("/{friendId}/wishlist/point")
    public ApiResponse<List<WishlistMapPoint>> getFriendWishlistPoint(
            HttpSession session,
            @PathVariable Long friendId
    ) {
        if (session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (long) session.getAttribute("userId");

        if (!friendService.isFriend(userId, friendId)) {
            return ApiResponse.fail("친구만 조회할 수 있습니다.");
        }
        return ApiResponse.success(
                "친구 위시리스트 좌표 조회 성공",
                wishlistService.getMyWishlistPoint(friendId)
        );
    }

}