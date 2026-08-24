package com.mink.projecttrip.post.service;

import com.mink.projecttrip.city.service.CityService;
import com.mink.projecttrip.common.FileManager;
import com.mink.projecttrip.post.domain.Post;
import com.mink.projecttrip.post.domain.PostImage;
import com.mink.projecttrip.post.repository.PostImageRepository;
import com.mink.projecttrip.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final CityService cityService;

    @Transactional
    public boolean createPost(long userId,
                              long countryId,
                              String contents,
                              String cityName,
                              String atmosphere,
                              String placeName,
                              String musicUrl,
                              List<MultipartFile> images){

        double[] coordinate =
                cityService.getCoordinate(countryId, cityName);

        Post post = Post.builder()
                .userId(userId)
                .countryId(countryId)
                .contents(contents)
                .cityName(cityName)
                .atmosphere(atmosphere)
                .placeName(placeName)
                .musicUrl(musicUrl)
                .latitude(coordinate[0])
                .longitude(coordinate[1])
                .build();

        postRepository.save(post);

        if(images != null && !images.isEmpty()){
            List<String> savedImagePaths = new ArrayList<>();
            int sortOrder = 0;

            for(MultipartFile image : images){
                if(image == null || image.isEmpty()){
                    continue;
                }

                String imagePath = FileManager.saveFile(userId, image);

                if(imagePath == null){
                    for(String path : savedImagePaths){
                        FileManager.removeFile(path);
                    }
                    throw new IllegalStateException("이미지 저장에 실패했습니다.");
                }

                savedImagePaths.add(imagePath);

                PostImage postImage = PostImage.builder()
                        .postId(post.getId())
                        .imagePath(imagePath)
                        .sortOrder(sortOrder++)
                        .build();

                postImageRepository.save(postImage);
            }
        }

        return true;
    }
}