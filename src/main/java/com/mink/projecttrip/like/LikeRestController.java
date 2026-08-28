package com.mink.projecttrip.like;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.like.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeRestController {

    private final LikeService likeService;

    @PostMapping("/post/like")
    public ApiResponse<Void> likePost(
            @RequestParam long postId,
            HttpSession session){


        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");


        if(likeService.createLikePost(postId,userId)){
            return ApiResponse.success("좋아요 성공");
        }else {
            return ApiResponse.fail("좋아요 실패");
        }
    }

    @DeleteMapping("/post/unlike")
    public ApiResponse<Void> unlikePost(
            @RequestParam long postId,
            HttpSession session){

        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");

        if(likeService.deleteLikePost(postId,userId)){
            return ApiResponse.success("좋아요 취소 성공");
        }else {
            return ApiResponse.fail("좋아요 취소 실패");
        }
    }

}
