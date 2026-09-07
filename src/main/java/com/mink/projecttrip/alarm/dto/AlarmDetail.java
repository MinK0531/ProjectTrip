package com.mink.projecttrip.alarm.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmDetail {
    private long id;
    private long fromUserId;
    private String fromUserNickName;
    private String fromUserProfileImg;
    private long postId;
    private String type;
    private String action;
    private boolean read;
    private LocalDateTime createdAt;
}