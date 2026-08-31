package com.mink.projecttrip.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentDetail {
    private long id;
    private long userId;
    private String nickName;
    private String comment;


}
