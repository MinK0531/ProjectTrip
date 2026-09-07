package com.mink.projecttrip.alarm.service;

import com.mink.projecttrip.alarm.domain.Alarm;
import com.mink.projecttrip.alarm.dto.AlarmDetail;
import com.mink.projecttrip.alarm.repository.AlarmRepository;
import com.mink.projecttrip.friend.domain.FriendRequest;
import com.mink.projecttrip.friend.repository.FriendRequestRepository;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.domain.UserProfile;
import com.mink.projecttrip.user.repository.UserProfileRepository;
import com.mink.projecttrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AlarmService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AlarmRepository alarmRepository;
    @Transactional(readOnly = true)
    public List<AlarmDetail> getAlarmList(Long userId) {

        List<Alarm> alarms =
                alarmRepository.findByUserIdOrderByCreatedAtDesc(userId);

        List<AlarmDetail> result = new ArrayList<>();

        for (Alarm alarm : alarms) {

            User user = userRepository.findById(alarm.getFromUserId()).orElse(null);

            if (user == null) {
                continue;
            }
            UserProfile profile = userProfileRepository.findByUserId(user.getId());
            String profileImg = "/img/profile.png";
            if (profile != null
                    && profile.getProfileImg() != null
                    && !profile.getProfileImg().isBlank()) {

                profileImg = profile.getProfileImg();
            }
            result.add(
                    AlarmDetail.builder()
                            .id(alarm.getId())
                            .fromUserId(user.getId())
                            .fromUserNickName(user.getNickName())
                            .fromUserProfileImg(profileImg)
                            .postId(alarm.getPostId())
                            .type(alarm.getType())
                            .action(alarm.getAction())
                            .read(alarm.isRead())
                            .createdAt(alarm.getCreatedAt())
                            .build()
            );
        }

        return result;
    }
    @Transactional
    public void createLikeAlarm(Long postOwnerId, Long fromUserId, Long postId) {

        if (postOwnerId.equals(fromUserId)) {
            return;
        }

        Alarm alarm = Alarm.builder()
                .userId(postOwnerId)
                .fromUserId(fromUserId)
                .postId(postId)
                .type(Alarm.LIKE)
                .isRead(false)
                .build();

        alarmRepository.save(alarm);
    }
    @Transactional
    public void createCommentAlarm(Long postOwnerId, Long fromUserId, Long postId) {

        if (postOwnerId.equals(fromUserId)) {
            return;
        }

        Alarm alarm = Alarm.builder()
                .userId(postOwnerId)
                .fromUserId(fromUserId)
                .postId(postId)
                .type(Alarm.COMMENT)
                .isRead(false)
                .build();

        alarmRepository.save(alarm);
    }

    @Transactional
    public void createFriendRequestAlarm(Long toUserId, Long fromUserId) {
        if (toUserId.equals(fromUserId)) {
            return;
        }
        Alarm alarm = Alarm.builder()
                .userId(toUserId)
                .fromUserId(fromUserId)
                .postId(0L)
                .type(Alarm.FRIEND)
                .action(Alarm.REQUEST)
                .isRead(false)
                .build();
        alarmRepository.save(alarm);
    }
    @Transactional
    public void createFriendRequestSentAlarm(Long fromUserId, Long toUserId) {
        Alarm alarm = Alarm.builder()
                .userId(fromUserId)
                .fromUserId(toUserId)
                .postId(0L)
                .type(Alarm.FRIEND)
                .action(Alarm.REQUEST_SENT)
                .isRead(true)
                .build();

        alarmRepository.save(alarm);
    }
    @Transactional
    public void updateFriendRequestAlarm(Long userId, Long fromUserId, String action) {

        Alarm alarm = alarmRepository.findByUserIdAndFromUserIdAndTypeAndAction(
                        userId,
                        fromUserId,
                        Alarm.FRIEND,
                        Alarm.REQUEST
                )
                .orElse(null);

        if (alarm == null) {
            return;
        }

        alarm.setAction(action);
        alarm.setRead(true);
    }
    @Transactional
    public void createFriendAcceptAlarm(Long toUserId, Long fromUserId) {

        Alarm alarm = Alarm.builder()
                .userId(toUserId)
                .fromUserId(fromUserId)
                .postId(0L)
                .type(Alarm.FRIEND)
                .action(Alarm.ACCEPT)
                .isRead(false)
                .build();

        alarmRepository.save(alarm);
    }
    @Transactional
    public void createFriendRejectAlarm(Long toUserId, Long fromUserId) {

        Alarm alarm = Alarm.builder()
                .userId(toUserId)
                .fromUserId(fromUserId)
                .postId(0L)
                .type(Alarm.FRIEND)
                .action(Alarm.REJECT)
                .isRead(false)
                .build();

        alarmRepository.save(alarm);
    }

    @Transactional
    public void readAlarm(Long alarmId, Long userId) {

        Alarm alarm = alarmRepository.findById(alarmId).orElse(null);

        if(alarm == null) {
            return;
        }


        if (!Long.valueOf(alarm.getUserId()).equals(userId)) {
            return;
        }

        alarm.setRead(true);
    }

}
