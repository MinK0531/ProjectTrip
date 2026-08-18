package com.mink.projecttrip.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {
    @GetMapping("/timeline")
    public String timeline(){
        return "search/timeline";
    }
}
