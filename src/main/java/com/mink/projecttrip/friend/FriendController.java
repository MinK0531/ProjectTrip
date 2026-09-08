package com.mink.projecttrip.friend;

import com.mink.projecttrip.post.repository.PostRepository;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.domain.UserProfile;
import com.mink.projecttrip.user.repository.UserProfileRepository;
import com.mink.projecttrip.user.repository.UserRepository;
import com.mink.projecttrip.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final PostRepository postRepository;
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    @GetMapping("/{userId}")
    public String friendMypage(
            @PathVariable Long userId,
            Model model) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserProfile userProfile = userProfileRepository.findByUserId(userId);

        int postCount = postRepository.countByUserId(userId);

        int wishlistCount = wishlistRepository.countByUserId(userId);

        model.addAttribute("friend", user);
        model.addAttribute("friendProfile", userProfile);

        model.addAttribute("postCount", postCount);
        model.addAttribute("wishlistCount", wishlistCount);

        return "friend/friendpage";
    }


}
