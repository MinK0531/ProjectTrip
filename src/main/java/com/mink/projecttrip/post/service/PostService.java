package com.mink.projecttrip.post.service;

import com.mink.projecttrip.city.service.CityService;
import com.mink.projecttrip.comment.dto.CommentDetail;
import com.mink.projecttrip.comment.service.CommentService;
import com.mink.projecttrip.common.FileManager;
import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.repository.CountryRepository;
import com.mink.projecttrip.like.service.LikeService;
import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.domain.PostImage;
import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.post.dto.PostImageDetail;
import com.mink.projecttrip.post.dto.PostMapPoint;
import com.mink.projecttrip.post.dto.PostTicketDetail;
import com.mink.projecttrip.post.repository.PostImageRepository;
import com.mink.projecttrip.post.repository.PostRepository;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.domain.UserProfile;
import com.mink.projecttrip.user.repository.UserProfileRepository;
import com.mink.projecttrip.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final CityService cityService;
    private final UserService userService;
    private final CountryRepository countryRepository;
    private final LikeService likeService;
    private final CommentService commentService;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public boolean createPost(long userId,
                              long countryId,
                              String contents,
                              String cityName,
                              String atmosphere,
                              String placeName,
                              String musicUrl,
                              List<MultipartFile> images){

        double[] coordinate =
                cityService.getCoordinate(countryId, cityName);

        Post post = Post.builder()
                .userId(userId)
                .countryId(countryId)
                .contents(contents)
                .cityName(cityName)
                .atmosphere(atmosphere)
                .placeName(placeName)
                .musicUrl(musicUrl)
                .latitude(coordinate[0])
                .longitude(coordinate[1])
                .build();

        postRepository.save(post);

        if(images != null && !images.isEmpty()){
            List<String> savedImagePaths = new ArrayList<>();
            int sortOrder = 0;

            for(MultipartFile image : images){
                if(image == null || image.isEmpty()){
                    continue;
                }

                String imagePath = FileManager.saveFile(userId, image);

                if(imagePath == null){
                    for(String path : savedImagePaths){
                        FileManager.removeFile(path);
                    }
                    throw new IllegalStateException("이미지 저장에 실패했습니다.");
                }

                savedImagePaths.add(imagePath);


                PostImage postImage = PostImage.builder()
                        .postId(post.getId())
                        .imagePath(imagePath)
                        .sortOrder(sortOrder++)
                        .build();

                postImageRepository.save(postImage);
            }
        }

        return true;
    }
    @Transactional
    public List<PostDetail> getFeedList(long userId) {

        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<PostDetail> feedList = new ArrayList<>();

        for (Post post : postList) {

            User user = userService.getUserById(post.getUserId());
            int likeCount = likeService.countByPostId(post.getId());
            boolean isLike = likeService.isLikeByPostIdAndUserId(post.getId(), userId);
            List<CommentDetail> commentList = commentService.getCommentList(post.getId(),userId);

            List<PostImage> images = postImageRepository
                    .findAllByPostIdOrderBySortOrderAsc(
                            post.getId()
                    );

            List<PostImageDetail> imageList = images.stream()
                    .map(image ->
                            PostImageDetail.builder()
                                    .id(image.getId())
                                    .imagePath(image.getImagePath())
                                    .sortOrder(image.getSortOrder())
                                    .build())
                    .toList();
            String countryName = countryRepository.findById(post.getCountryId())
                    .map(Country::getCountryNameKo)
                    .orElse("알 수 없는 나라");
            UserProfile userProfile = userProfileRepository.findByUserId(post.getUserId());

            String profileImg = userProfile != null ? userProfile.getProfileImg() : "/img/profile.png";

            PostDetail postDetail = PostDetail.builder()
                    .id(post.getId())
                    .userId(post.getUserId())
                    .countryId(post.getCountryId())
                    .nickName(user.getNickName())
                    .countryName(countryName)
                    .cityName(post.getCityName())
                    .contents(post.getContents())
                    .atmosphere(post.getAtmosphere())
                    .placeName(post.getPlaceName())
                    .musicUrl(post.getMusicUrl())
                    .latitude(post.getLatitude())
                    .longitude(post.getLongitude())
                    .createdAt(post.getCreatedAt())
                    .imageList(imageList)
                    .commentCount(commentList.size())
                    .profileImg(profileImg)
                    .likeCount(likeCount)
                    .isLike(isLike)
                    .build();
            feedList.add(postDetail);
        }
        return feedList;
    }
    @Transactional
    public PostDetail getPostDetail(long postId, long userId) {

        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            return null;
        }

        Post post = optionalPost.get();

        User user = userService.getUserById(userId);

        int likeCount = likeService.countByPostId(postId);
        boolean isLike = likeService.isLikeByPostIdAndUserId(postId, userId);
        List<CommentDetail> commentList = commentService.getCommentList(post.getId(),userId);

        List<PostImage> images = postImageRepository.findAllByPostIdOrderBySortOrderAsc(postId);

        List<PostImageDetail> imageList = images.stream()
                .map(image -> PostImageDetail.builder()
                        .id(image.getId())
                        .imagePath(image.getImagePath())
                        .sortOrder(image.getSortOrder())
                        .build())
                .toList();

        String countryName = countryRepository.findById(post.getCountryId())
                        .map(Country::getCountryNameKo)
                        .orElse("알 수 없는 나라");
        UserProfile userProfile = userProfileRepository.findByUserId(post.getUserId());

        String profileImg = userProfile != null ? userProfile.getProfileImg() : "/img/profile.png";

        return PostDetail.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .countryId(post.getCountryId())
                .nickName(user.getNickName())
                .countryName(countryName)
                .cityName(post.getCityName())
                .contents(post.getContents())
                .atmosphere(post.getAtmosphere())
                .placeName(post.getPlaceName())
                .musicUrl(post.getMusicUrl())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .createdAt(post.getCreatedAt())
                .imageList(imageList)
                .commentList(commentList)
                .profileImg(profileImg)
                .likeCount(likeCount)
                .isLike(isLike)
                .build();
    }

    @Transactional
    public boolean deletePost(long userId, long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);

        if(optionalPost.isPresent()){
            try{
                Post post = optionalPost.get();

                if(post.getUserId() != userId){
                    return false;
                }
                likeService.deleteLikeByPostId(post.getId());
                commentService.deleteCommentByPostId(post.getId());

                List<PostImage> imageList =
                        postImageRepository.findAllByPostIdOrderBySortOrderAsc(postId);
                for (PostImage image : imageList) {
                    if (image.getImagePath() != null) {
                        FileManager.removeFile(image.getImagePath());
                    }
                }
                postRepository.delete(post);
            }catch(DataAccessException e){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

    @Transactional
    public boolean updatePost(
            long userId,
            long postId,
            String contents,
            String cityName,
            String placeName,
            String atmosphere,
            String musicUrl,
            List<Long> deleteImageIds
    ) {

        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            return false;
        }
        Post post = optionalPost.get();

        if (post.getUserId() != userId) {
            return false;
        }

        post.setContents(contents);
        post.setCityName(cityName);
        post.setPlaceName(placeName);
        post.setAtmosphere(atmosphere);
        post.setMusicUrl(musicUrl);

        if (deleteImageIds != null) {
            for (Long imageId : deleteImageIds) {
                Optional<PostImage> optionalImage = postImageRepository.findById(imageId);
                if (optionalImage.isPresent()) {
                    PostImage image = optionalImage.get();
                    if (image.getPostId() != postId) {
                        continue;
                    }
                    FileManager.removeFile(image.getImagePath());
                    postImageRepository.delete(image);
                }
            }
        }
        return true;
    }

    @Transactional
    public List<PostMapPoint> getMyPostPoints(long userId){
        List<Post> postList = postRepository.findByUserIdOrderByIdDesc(userId);

        List<PostMapPoint> pointList = new ArrayList<>();

        for(Post post : postList){
            Optional<Country> optionalCountry = countryRepository.findById(post.getCountryId());
            if (optionalCountry.isEmpty()) {
                continue;
            }
            if(post.getLatitude() == 0 && post.getLongitude() == 0){
                continue;
            }
            Country country = optionalCountry.get();
            String countryName = countryRepository.findById(post.getCountryId())
                    .map(Country::getCountryNameKo)
                    .orElse("알 수 없는 나라");

            pointList.add(
                    PostMapPoint.builder()
                            .postId(post.getId())
                            .latitude(post.getLatitude())
                            .longitude(post.getLongitude())
                            .cityName(post.getCityName())
                            .countryName(countryName)
                            .countryCode(country.getCountryCode())
                            .build()
            );
        }
        return pointList;
    }

    @Transactional
    public List<PostTicketDetail> getCountryPostList(
            long userId,
            String countryCode) {

        User user = userService.getUserById(userId);

        if (user == null) {
            return new ArrayList<>();
        }

        Optional<Country> optionalCountry =
                countryRepository.findByCountryCode(countryCode);

        if (optionalCountry.isEmpty()) {
            return new ArrayList<>();
        }

        Country country = optionalCountry.get();

        List<Post> postList = postRepository.findByUserIdAndCountryIdOrderByIdDesc(
                        userId,
                        country.getId()
                );

        List<PostTicketDetail> ticketList = new ArrayList<>();

        for (Post post : postList) {

            if (post.getLatitude() == 0 && post.getLongitude() == 0) {
                continue;
            }

            Optional<PostImage> firstImage =
                    postImageRepository.findFirstByPostIdOrderBySortOrderAsc(
                            post.getId()
                    );

            String imageUrl = "/img/profile.png";

            if (firstImage.isPresent()) {
                imageUrl = firstImage.get().getImagePath();
            }

            ticketList.add(
                    PostTicketDetail.builder()
                            .postId(post.getId())
                            .imageUrl(imageUrl)
                            .createdAt(post.getCreatedAt())
                            .fromCountryCode(user.getCountryCode())
                            .toCountryCode(country.getCountryCode())
                            .build()
            );
        }

        return ticketList;
    }
}
