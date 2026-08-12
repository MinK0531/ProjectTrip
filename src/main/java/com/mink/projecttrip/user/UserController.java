package com.mink.projecttrip.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }
    @GetMapping("/signin")
    public String signin(){
        return "user/signin";
    }

}