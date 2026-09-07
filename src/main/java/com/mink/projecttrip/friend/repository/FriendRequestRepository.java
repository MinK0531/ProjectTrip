package com.mink.projecttrip.friend.repository;

import com.mink.projecttrip.friend.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    boolean existsByFromUserIdAndToUserIdAndStatus(long fromUserId, long toUserId, int status);
    Optional<FriendRequest> findByFromUserIdAndToUserIdAndStatus( long fromUserId, long toUserId, int status );
    List<FriendRequest> findByToUserIdAndStatus(long toUserId, int status);
}
