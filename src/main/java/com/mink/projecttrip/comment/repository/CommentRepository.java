package com.mink.projecttrip.comment.repository;

import com.mink.projecttrip.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPostId(long postId);
    public void deleteByPostId(long postId);

}
