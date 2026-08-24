package com.mink.projecttrip.post.repository;

import com.mink.projecttrip.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

}