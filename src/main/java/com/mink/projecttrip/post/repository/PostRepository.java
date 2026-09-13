package com.mink.projecttrip.post.repository;

import com.mink.projecttrip.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdOrderByIdDesc(long userId);
    public int countByUserId(long userId);
    List<Post> findByContentsContainingIgnoreCaseOrderByIdDesc(String contents);
    List<Post> findByUserIdAndCountryIdOrderByIdDesc(long userId, long countryId);
    List<Post> findByCountryIdInOrderByIdDesc(List<Long> countryIds);

}
