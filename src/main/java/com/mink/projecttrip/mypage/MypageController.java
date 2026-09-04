package com.mink.projecttrip.mypage;


import com.mink.projecttrip.post.repository.PostRepository;
import com.mink.projecttrip.wishlist.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {

    private final PostRepository postRepository;
    private final WishlistRepository wishlistRepository;

    @GetMapping("/map")

    public String map(HttpSession session, Model model)
    {
        Long userId = (Long)session.getAttribute("userId");
        if(userId == null){
            return "redirect:/login";
        }
        int postCount = postRepository.countByUserId(userId);
        int wishlistCount = wishlistRepository.countByUserId(userId);

        model.addAttribute("postCount", postCount);
        model.addAttribute("wishlistCount",wishlistCount);

        return "/mypage/map";
    }
}
