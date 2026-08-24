package com.mink.projecttrip.post.repository;

import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findAllByPostIdOrderBySortOrderAsc(long postId);
}