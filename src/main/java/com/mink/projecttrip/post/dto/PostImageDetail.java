package com.mink.projecttrip.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostImageDetail {
    private long id;
    private String imagePath;
    private int sortOrder;
}