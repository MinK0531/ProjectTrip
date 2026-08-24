package com.mink.projecttrip.post;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
