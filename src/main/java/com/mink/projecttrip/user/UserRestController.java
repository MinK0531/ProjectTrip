package com.mink.projecttrip.user;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    @PostMapping("/signup-process")
    public ApiResponse<Void> signup(
            @RequestParam String nickName,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String countryCode,
            @RequestParam String email){

        if(userService.createUser(nickName, password, name, countryCode, email)){
            return ApiResponse.success("회원가입 성공");
        }else{
            return ApiResponse.fail("회원가입 실패");
        }
    }

    @GetMapping("/duplicate-nickname")
    public ApiResponse<Boolean> isDuplicateNickName(
            @RequestParam String nickName){

        if(userService.isDuplicateNickName(nickName)){
            return ApiResponse.success("중복된 닉네임", true);
        }else{
            return ApiResponse.success("사용 가능한 닉네임", false);
        }
    }

    @GetMapping("/duplicate-email")
    public ApiResponse<Boolean> isDuplicateEmail(
            @RequestParam String email){

        if (userService.isDuplicateEmail(email)) {
            return ApiResponse.success("중복된 이메일", true);
        }else{
            return ApiResponse.success("사용 가능한 이메일", false);
        }
    }


}
