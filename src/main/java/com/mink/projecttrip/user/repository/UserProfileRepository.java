package com.mink.projecttrip.user.repository;

import com.mink.projecttrip.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

    public UserProfile findByUserId(long userId);


}

