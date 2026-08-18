package com.mink.projecttrip.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

    @GetMapping("/list")
    public String list(){
        return "message/list";
    }
}
