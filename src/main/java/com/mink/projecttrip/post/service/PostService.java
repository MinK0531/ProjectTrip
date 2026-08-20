package com.mink.projecttrip.post.service;

import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public boolean createPost(long userId,
                              long countryId,
                              String contents,
                              String cityName,
                              String atmosphere,
                              String placeName,
                              String musicUrl,
                              double latitude,
                              double longitude){

        Post post = Post.builder()
                .userId(userId)
                .countryId(countryId)
                .contents(contents)
                .cityName(cityName)
                .atmosphere(atmosphere)
                .placeName(placeName)
                .musicUrl(musicUrl)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        try{
            postRepository.save(post);
        }catch(DataAccessException e){
            return false;
        }
        return true;
    }
}
