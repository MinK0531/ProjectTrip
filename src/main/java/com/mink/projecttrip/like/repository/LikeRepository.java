package com.mink.projecttrip.like.repository;

import com.mink.projecttrip.like.domain.Like;
import com.mink.projecttrip.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    public int countByPostId(long postId);
    public boolean existsByPostIdAndUserId(long postId, long userId);
    public void deleteByPostId(long postId);
}
