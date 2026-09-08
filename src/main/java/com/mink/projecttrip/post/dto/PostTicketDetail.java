package com.mink.projecttrip.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTicketDetail {
    private long postId;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String fromCountryCode;
    private String toCountryCode;
}
