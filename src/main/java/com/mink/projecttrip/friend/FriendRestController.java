package com.mink.projecttrip.friend;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.friend.domain.Friend;
import com.mink.projecttrip.friend.domain.FriendRequest;
import com.mink.projecttrip.friend.service.FriendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendRestController {

    private final FriendService friendService;


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

}