package com.mink.projecttrip.wishlist;

import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlist")
public class WishlistRestController {

    private final WishlistService wishlistService;

    @PostMapping("/create")
    public ApiResponse<Void> create(
            @RequestParam long countryId,
            @RequestParam(required = false) String cityName,
            @RequestParam String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String memo,
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if (wishlistService.createWishlist(
                userId,
                countryId,
                cityName,
                period,
                startDate,
                endDate,
                memo)) {
            return ApiResponse.success("위시리스트 등록 성공");
        } else {
            return ApiResponse.fail("위시리스트 등록 실패");

        }
    }
    @DeleteMapping("/remove")
    public ApiResponse<Void> remove(
            @RequestParam long wishlistId,
            HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("userId") == null){
            return ApiResponse.fail("로그인이 필요합니다");
        }

        long userId = (Long) session.getAttribute("userId");

        if(wishlistService.deleteWishlist(userId, wishlistId)){
            return ApiResponse.success("삭제 성공");
        }else{
            return ApiResponse.fail("삭제 실패");
        }
    }
    @PutMapping("/modify")
    public ApiResponse<Void> modify(
            @RequestParam long wishlistId,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String memo,
            HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        if(wishlistService.updateWishlist(
                userId,
                wishlistId,
                cityName,
                startDate,
                endDate,
                memo)){
            return ApiResponse.success("수정 성공");
        }else{
            return ApiResponse.fail("수정 실패");
        }
    }
}

