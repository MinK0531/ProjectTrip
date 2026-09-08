package com.mink.projecttrip.post.repository;

import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findAllByPostIdOrderBySortOrderAsc(long postId);
    Optional<PostImage> findFirstByPostIdOrderBySortOrderAsc(long postId);
}