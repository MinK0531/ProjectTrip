package com.mink.projecttrip.post;

import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/detail")
    public String postDetail(
            @RequestParam long postId,
            HttpSession session,
            Model model) {

        long userId = (Long) session.getAttribute("userId");

        PostDetail post = postService.getPostDetail(postId, userId);

        model.addAttribute("post", post);

        return "post/post_card :: postDetail";
    }
    @GetMapping("/modify-modal")
    public String modifyModal(
            @RequestParam long postId,
            HttpSession session,
            Model model) {

        long userId = (Long) session.getAttribute("userId");

        PostDetail post = postService.getPostDetail(postId, userId);
        model.addAttribute("post", post);

        if (post == null || post.getUserId() != userId) {
            throw new IllegalArgumentException("수정 권한이 없거나 존재하지 않는 게시물");
        }
        return "post/post_modify :: modifyPostModal";
    }
}
