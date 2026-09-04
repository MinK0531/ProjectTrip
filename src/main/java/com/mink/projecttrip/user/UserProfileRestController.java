package com.mink.projecttrip.user;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.user.dto.UserProfileDetail;
import com.mink.projecttrip.user.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserProfileRestController {
    private final UserProfileService userProfileService;

    @GetMapping("/profile")
    public ApiResponse<UserProfileDetail> getProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        UserProfileDetail pofile = userProfileService.getMyProfile(userId);
        return ApiResponse.success("프로필 조회 성공", pofile);

    }

    @PutMapping("/profile-modify")
    public ApiResponse<Void> modifyProfile(
            @RequestParam (required = false) String profileWord,
            @RequestParam (required = false) MultipartFile profileImg,
            @RequestParam(required = false, defaultValue = "false") boolean deleteProfileImg,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if(userProfileService.updateMyProfile(
                userId,
                profileWord,
                profileImg,
                deleteProfileImg)) {
            UserProfileDetail profile = userProfileService.getMyProfile(userId);
            session.setAttribute( "userProfileImg", profile.getProfileImg() != null ? profile.getProfileImg() : "/img/profile.png" );
            session.setAttribute( "userProfileWord", profile.getProfileWord() );

            return ApiResponse.success("프로필 수정 성공");
        }else{
            return ApiResponse.fail("프로필 수정 실패");
        }
    }


}
