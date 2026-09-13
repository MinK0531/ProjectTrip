package com.mink.projecttrip.wishlist.service;

import com.mink.projecttrip.city.service.CityService;

import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.repository.CountryRepository;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.service.UserService;
import com.mink.projecttrip.wishlist.domain.Wishlist;
import com.mink.projecttrip.wishlist.dto.WishlistDetail;
import com.mink.projecttrip.wishlist.dto.WishlistMapPoint;
import com.mink.projecttrip.wishlist.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CityService cityService;
    private final UserService userService;
    private final CountryRepository countryRepository;

    public boolean createWishlist(
            long userId,
            long countryId,
            String cityName,
            String period,
            String startDate,
            String endDate,
            String memo
    ) {
        double[] coordinate =
                cityService.getCoordinate(countryId, cityName);

        Wishlist wishlist = Wishlist.builder()
                .userId(userId)
                .countryId(countryId)
                .cityName(cityName)
                .period(period)
                .startDate(startDate == null || startDate.isBlank() ? null : LocalDate.parse(startDate))
                .endDate(endDate == null || endDate.isBlank() ? null : LocalDate.parse(endDate))
                .memo(memo)
                .latitude(coordinate[0])
                .longitude(coordinate[1])
                .build();

        try {
            wishlistRepository.save(wishlist);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public List<WishlistDetail> getFeedList(long userId){
        List<Wishlist> wishList = wishlistRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<WishlistDetail> feedList = new ArrayList<>();

        for(Wishlist wish : wishList){
            User user = userService.getUserById(wish.getUserId());
            String countryName = countryRepository.findById(wish.getCountryId())
                    .map(Country::getCountryNameKo)
                    .orElse("알 수 없는 나라");

            WishlistDetail wishlistDetail = WishlistDetail.builder()
                    .id(wish.getId())
                    .userId(wish.getUserId())
                    .countryId(wish.getCountryId())
                    .nickName(user.getNickName())
                    .countryName(countryName)
                    .cityName(wish.getCityName())
                    .memo(wish.getMemo())
                    .period(wish.getPeriod())
                    .startDate(wish.getStartDate())
                    .endDate(wish.getEndDate())
                    .latitude(wish.getLatitude())
                    .longitude(wish.getLongitude())
                    .createdAt(wish.getCreatedAt())
                    .build();
            feedList.add(wishlistDetail);
        }
        return feedList;
    }
    @Transactional
    public WishlistDetail getWishlistDetail(long wishlistId, long userId){
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        if(optionalWishlist.isEmpty()){
            return null;
        }
        Wishlist wishlist = optionalWishlist.get();

        if (wishlist.getUserId() != userId) {
            return null;
        }

        String countryName = countryRepository.findById(wishlist.getCountryId())
                .map(Country::getCountryNameKo)
                .orElse("알 수 없는 나라");
        return WishlistDetail.builder()
                .id(wishlist.getId())
                .countryId(wishlist.getCountryId())
                .countryName(countryName)
                .cityName(wishlist.getCityName())
                .period(wishlist.getPeriod())
                .startDate(wishlist.getStartDate())
                .endDate(wishlist.getEndDate())
                .memo(wishlist.getMemo())
                .build();
    }


    @Transactional
    public boolean deleteWishlist(long userId, long wishlistId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);

        if(optionalWishlist.isPresent()){
            try {
                Wishlist wishlist =optionalWishlist.get();

                if(wishlist.getUserId() != userId){
                    return false;
                }
                wishlistRepository.delete(wishlist);
            }catch (DataAccessException e){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    @Transactional
    public boolean updateWishlist(
            long userId,
            long wishlistId,
            String cityName,
            String startDate,
            String endDate,
            String memo){
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        if(optionalWishlist.isEmpty()){
            return false;
        }
        Wishlist wishlist = optionalWishlist.get();
        if(wishlist.getUserId() != userId){
            return false;
        }
        wishlist.setCityName(cityName);
        wishlist.setStartDate(startDate == null || startDate.isBlank() ? null : LocalDate.parse(startDate));
        wishlist.setEndDate(endDate == null || endDate.isBlank() ? null : LocalDate.parse(endDate));
        wishlist.setMemo(memo);
        return true;
    }

    @Transactional
    public List<WishlistMapPoint> getMyWishlistPoint(long userId) {
        List<Wishlist> wishlistList = wishlistRepository.findByUserIdOrderByIdDesc(userId);

        List<WishlistMapPoint> pointList = new ArrayList<>();

        for(Wishlist wishlist : wishlistList){
            if(wishlist.getLatitude()== 0 && wishlist.getLongitude()== 0){
                continue;
            }
            Optional<Country> optionalCountry = countryRepository.findById(wishlist.getCountryId());


            Country country = optionalCountry.get();
            pointList.add(
                    WishlistMapPoint.builder()
                            .wishlistId(wishlist.getId())
                            .latitude(wishlist.getLatitude())
                            .longitude(wishlist.getLongitude())
                            .cityName(wishlist.getCityName())
                            .countryName(country.getCountryNameKo())
                            .countryCode(country.getCountryCode())
                            .build()

            );
        }
        return pointList;

    }
    @Transactional
    public List<WishlistDetail>  getCountryWishList(long userId, String countryCode){
        User user = userService.getUserById(userId);
        if(user == null){
            return new ArrayList<>();
        }
        Optional<Country> optionalCountry = countryRepository.findByCountryCode(countryCode);
        if(optionalCountry.isEmpty()){
            return new ArrayList<>();
        }
        Country country = optionalCountry.get();

        List<Wishlist> wishlistList = wishlistRepository.findByUserIdAndCountryIdOrderByIdDesc(userId, country.getId());

        List<WishlistDetail> ticketList = new ArrayList<>();
        for (Wishlist wishlist : wishlistList) {
            ticketList.add(WishlistDetail.builder()
                    .id(wishlist.getId())
                    .userId(wishlist.getUserId())
                    .countryId(wishlist.getCountryId())
                    .nickName(user.getNickName())
                    .countryName(country.getCountryNameKo())
                    .cityName(wishlist.getCityName())
                    .memo(wishlist.getMemo())
                    .period(wishlist.getPeriod())
                    .startDate(wishlist.getStartDate())
                    .endDate(wishlist.getEndDate())
                    .latitude(wishlist.getLatitude())
                    .longitude(wishlist.getLongitude())
                    .createdAt(wishlist.getCreatedAt())
                    .build());
        }
        return ticketList;
    }


}
