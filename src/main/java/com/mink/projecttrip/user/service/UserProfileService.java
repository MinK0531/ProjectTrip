package com.mink.projecttrip.user.service;

import com.mink.projecttrip.common.FileManager;
import com.mink.projecttrip.user.domain.UserProfile;
import com.mink.projecttrip.user.dto.UserProfileDetail;
import com.mink.projecttrip.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public UserProfileDetail getMyProfile(long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId);

        if(profile==null){
            return UserProfileDetail.builder()
                    .profileWord("")
                    .profileImg("/img/profile.png")
                    .build();
        }

        return UserProfileDetail.builder()
                .profileWord(profile.getProfileWord())
                .profileImg(profile.getProfileImg())
                .build();
    }


    @Transactional
    public boolean updateMyProfile(
            long userId,
            String profileWord,
            MultipartFile profileImg,
            boolean deleteProfileImg
    ) {
        UserProfile profile = userProfileRepository.findByUserId(userId);

        if (profile == null) {
            String profileImgPath = "/img/profile.png";

            if (profileImg != null && !profileImg.isEmpty()) {
                profileImgPath = FileManager.saveFile(userId, profileImg);
            }

            profile = UserProfile.builder()
                    .userId(userId)
                    .profileWord(profileWord)
                    .profileImg(profileImgPath)
                    .build();

        } else {
            profile.setProfileWord(profileWord);

            if (deleteProfileImg) {
                profile.setProfileImg("/img/profile.png");
            } else if (profileImg != null && !profileImg.isEmpty()) {
                String profileImgPath = FileManager.saveFile(userId, profileImg);
                profile.setProfileImg(profileImgPath);
            }
        }

        userProfileRepository.save(profile);

        return true;
    }
}
