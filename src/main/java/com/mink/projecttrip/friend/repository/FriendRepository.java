package com.mink.projecttrip.friend.repository;

import com.mink.projecttrip.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {

    boolean existsByUserIdAndFriendUserId(long userId, long friendUserId);

    }
