package com.mink.projecttrrip.user;

import com.mink.projecttrrip.common.dto.ApiResponse;
import com.mink.projecttrrip.user.repository.UserRepository;
import com.mink.projecttrrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
