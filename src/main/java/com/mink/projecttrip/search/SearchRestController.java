package com.mink.projecttrip.search;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.search.dto.SearchUser;
import com.mink.projecttrip.search.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchRestController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ApiResponse<Map<String, Object>> search(
            @RequestParam String keyword,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if (keyword == null || keyword.isBlank()) {
            Map<String, Object> result = new HashMap<>();
            result.put("users", List.of());
            result.put("posts", List.of());
            result.put("countryPosts", List.of());
            return ApiResponse.success("검색어를 입력해주세요.", result);
        }

        keyword = keyword.trim();
        List<SearchUser> users = searchService.searchUsers(keyword, userId);
        List<PostDetail> posts = searchService.searchPosts(keyword, userId);
        List<PostDetail> countryPosts = searchService.searchCountryPosts(keyword, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("posts", posts);
        result.put("countryPosts", countryPosts);
        return ApiResponse.success("검색 성공", result);
    }

}
