package com.mink.projecttrip.home;

import com.mink.projecttrip.home.service.HomeService;
import com.mink.projecttrip.post.service.PostService;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/timeline")
    public String timeline(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/user/signin";
        }

        model.addAttribute("feedList", homeService.getFeedList(userId));


        return "home/timeline";
    }
}

