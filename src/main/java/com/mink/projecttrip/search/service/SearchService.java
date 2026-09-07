package com.mink.projecttrip.search.service;

import com.mink.projecttrip.comment.dto.CommentDetail;
import com.mink.projecttrip.comment.service.CommentService;
import com.mink.projecttrip.country.domain.Country;
import com.mink.projecttrip.country.repository.CountryRepository;
import com.mink.projecttrip.friend.domain.FriendRequest;
import com.mink.projecttrip.friend.repository.FriendRepository;
import com.mink.projecttrip.friend.repository.FriendRequestRepository;
import com.mink.projecttrip.like.service.LikeService;
import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.domain.PostImage;
import com.mink.projecttrip.post.dto.PostDetail;
import com.mink.projecttrip.post.dto.PostImageDetail;
import com.mink.projecttrip.post.repository.PostImageRepository;
import com.mink.projecttrip.post.repository.PostRepository;
import com.mink.projecttrip.search.dto.SearchUser;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.domain.UserProfile;
import com.mink.projecttrip.user.repository.UserProfileRepository;
import com.mink.projecttrip.user.repository.UserRepository;
import com.mink.projecttrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Transactional
    public List<SearchUser> searchUsers(String keyword, Long currentUserId) {
        List<User> userList = userRepository.findByNickNameContaining(keyword);
        List<SearchUser> result = new ArrayList<>();
        for (User user : userList) {

            if (user.getId() == currentUserId) {
                continue;
            }

            UserProfile profile = userProfileRepository.findByUserId(user.getId());
            String profileImg = "/img/profile.png";
            if (profile != null && profile.getProfileImg() != null && !profile.getProfileImg().isBlank()) {
                profileImg = profile.getProfileImg();
            }

            boolean isFriend = friendRepository.existsByUserIdAndFriendUserId(
                            currentUserId,
                            user.getId());

            boolean requestSent = friendRequestRepository.existsByFromUserIdAndToUserIdAndStatus(
                                    currentUserId,
                                    user.getId(),
                                    FriendRequest.WAITING);

            result.add(
                    SearchUser.builder()
                            .id(user.getId())
                            .nickName(user.getNickName())
                            .profileImg(profileImg)
                            .friend(isFriend)
                            .requestSent(requestSent)
                            .build()
            );
        }
        return result;
    }



}
