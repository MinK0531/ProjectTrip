package com.mink.projecttrip.search;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {
    @GetMapping("/timeline")
    public String timeline(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        model.addAttribute("userId", userId);

        return "search/timeline";
    }
}
