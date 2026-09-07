package com.mink.projecttrip.like.service;

import com.mink.projecttrip.alarm.service.AlarmService;
import com.mink.projecttrip.like.domain.Like;
import com.mink.projecttrip.like.domain.LikeId;
import com.mink.projecttrip.like.repository.LikeRepository;
import com.mink.projecttrip.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final AlarmService alarmService;
    public boolean createLikePost(long postId, long userId){

        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .build();

        try{
            likeRepository.save(like);
        }catch(DataAccessException e){
            return false;
        }
        postRepository.findById(postId).ifPresent(post ->
                alarmService.createLikeAlarm(post.getUserId(), userId, postId)
        );

        return true;
    }

    public boolean deleteLikePost(long postId, long userId){
        LikeId likeId = LikeId.builder()
                .postId(postId)
                .userId(userId)
                .build();

        Optional<Like> optionalLike = likeRepository.findById(likeId);

        if(optionalLike.isPresent()){

            try{
                likeRepository.delete(optionalLike.get());
            }catch(DataAccessException e){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

    public int countByPostId(long postId){
        return likeRepository.countByPostId(postId);
    }
    public boolean isLikeByPostIdAndUserId(long postId, long userId){
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Transactional
    public void deleteLikeByPostId(long postId){
        likeRepository.deleteByPostId(postId);
    }
}
