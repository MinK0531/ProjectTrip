package com.mink.projecttrip.post;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping("/write-process")
    public ApiResponse<Void> write(
            @RequestParam long countryId,
            @RequestParam String contents,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String atmosphere,
            @RequestParam(required = false) String placeName,
            @RequestParam(required = false) String musicUrl,
            @RequestParam(required = false) List<MultipartFile> images,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("userId") == null){
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (long)session.getAttribute("userId");

        try{
            if(postService.createPost(
                    userId,
                    countryId,
                    contents,
                    cityName,
                    atmosphere,
                    placeName,
                    musicUrl,
                    images
            )){
                return ApiResponse.success("게시물 등록 성공");
            }else{
                return ApiResponse.fail("게시물 등록 실패");
            }
        }catch(Exception e){
            return ApiResponse.fail("게시물 등록 실패");
        }
    }


    @DeleteMapping("/remove")
    public ApiResponse<Void> remove(
            @RequestParam long postId,
            HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("userId") == null){
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long)session.getAttribute("userId");

        if(postService.deletePost(userId, postId)){
            return ApiResponse.success("삭제 성공");
        }else{
            return ApiResponse.fail("삭제 실패");
        }

    }

    @PutMapping("/modify")
    public ApiResponse<Void> modify(
            @RequestParam long postId,
            @RequestParam String contents,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String placeName,
            @RequestParam(required = false) String atmosphere,
            @RequestParam(required = false) String musicUrl,
            @RequestParam(required = false) List<Long> deleteImageIds,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if(postService.updatePost(
                userId,
                postId,
                contents,
                cityName,
                placeName,
                atmosphere,
                musicUrl,
                deleteImageIds)){
            return ApiResponse.success("수정 성공");
        }else{
            return ApiResponse.fail("수정 실패");
        }
    }
}
