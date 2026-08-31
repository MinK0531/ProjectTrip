package com.mink.projecttrip.comment.service;

import com.mink.projecttrip.comment.domain.Comment;
import com.mink.projecttrip.comment.dto.CommentDetail;
import com.mink.projecttrip.comment.repository.CommentRepository;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    final CommentRepository commentRepository;
    final UserService userService;

    public List<CommentDetail> getCommentList(long postId ,long userId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentDetail> commentDetailList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = userService.getUserById(comment.getUserId());
            CommentDetail commentDetail = CommentDetail.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .nickName(user.getNickName())
                    .comment(comment.getComment())
                    .build();
            commentDetailList.add(commentDetail);
        }
        return commentDetailList;
    }
    public boolean createComment(long postId, long userId, String comments){

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .comment(comments)
                .build();

        try{
            commentRepository.save(comment);
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Transactional
    public void deleteCommentByPostId(long postId) {
        commentRepository.deleteByPostId(postId);
    }
}
