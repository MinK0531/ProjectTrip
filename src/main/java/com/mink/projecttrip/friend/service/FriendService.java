package com.mink.projecttrip.friend.service;

import com.mink.projecttrip.alarm.domain.Alarm;
import com.mink.projecttrip.alarm.service.AlarmService;
import com.mink.projecttrip.friend.domain.Friend;
import com.mink.projecttrip.friend.domain.FriendRequest;
import com.mink.projecttrip.friend.repository.FriendRepository;
import com.mink.projecttrip.friend.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final AlarmService alarmService;

    @Transactional
    public boolean request(long fromUserId, long toUserId) {

        if (fromUserId == toUserId) {
            return false;
        }

        if (friendRepository.existsByUserIdAndFriendUserId(fromUserId, toUserId)) {
            return false;
        }

        if (friendRequestRepository.existsByFromUserIdAndToUserIdAndStatus(
                fromUserId,
                toUserId,
                FriendRequest.WAITING)) {
            return false;
        }

        FriendRequest request = FriendRequest.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .status(FriendRequest.WAITING)
                .build();

        alarmService.createFriendRequestAlarm(
                toUserId,
                fromUserId
        );


        alarmService.createFriendRequestSentAlarm(
                fromUserId,
                toUserId);
        friendRequestRepository.save(request);

        return true;
    }

    @Transactional
    public boolean accept(long fromUserId, long userId) {
        FriendRequest request = friendRequestRepository.findByFromUserIdAndToUserIdAndStatus(
                fromUserId
                ,userId
                , FriendRequest.WAITING).orElse(null);

        if(request == null) {
            return false;
        }
        long toUserId = request.getToUserId();

        request.accept();


        Friend friend1 = Friend.builder()
                .userId(fromUserId)
                .friendUserId(toUserId)
                .build();
        Friend friend2 = Friend.builder()
                .userId(toUserId)
                .friendUserId(fromUserId)
                .build();

        friendRepository.save(friend1);
        friendRepository.save(friend2);

        alarmService.updateFriendRequestAlarm(
                userId,
                fromUserId,
                Alarm.ACCEPT
        );

        alarmService.createFriendAcceptAlarm(
                fromUserId,
                userId
        );

        return true;
    }
    @Transactional
    public boolean reject(long fromUserId, long userId) {

        FriendRequest request = friendRequestRepository.findByFromUserIdAndToUserIdAndStatus(
                fromUserId,
                userId,
                FriendRequest.WAITING).orElse(null);
        if(request == null) {
            return false;
        }
        request.reject();

        alarmService.updateFriendRequestAlarm(
                userId,
                fromUserId,
                Alarm.REJECT
        );

        alarmService.createFriendRejectAlarm(
                fromUserId,
                userId
        );
        return  true;
    }
}
