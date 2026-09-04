package com.mink.projecttrip.wishlist;

import com.mink.projecttrip.wishlist.dto.WishlistDetail;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/modify-wishlist")
    public String modifyModel(
            @RequestParam long wishlistId,
            HttpSession session,
            Model model){
        long userId = (Long) session.getAttribute("userId");

        WishlistDetail wish = wishlistService.getWishlistDetail(wishlistId, userId);

        model.addAttribute("wish", wish);
        return "wishlist/wish_modify :: modifyWishModal";

    }
}
