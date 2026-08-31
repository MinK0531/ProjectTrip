package com.mink.projecttrip.comment;

import com.mink.projecttrip.comment.service.CommentService;
import com.mink.projecttrip.common.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {

    private final CommentService commentService;
    @PostMapping("/write")
    public ApiResponse<Void> writeComment(
            @RequestParam long postId
            , @RequestParam String comments
            , HttpSession session){

        if(session == null || session.getAttribute("userId") == null){
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if(commentService.createComment(postId, userId, comments)){
            return ApiResponse.success("댓글 성공");
        }else{
            return ApiResponse.fail("댓글 실패");
        }
    }

}
