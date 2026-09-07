package com.mink.projecttrip.alarm.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@Table(name = "`alarm`")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class Alarm {

    public static final String LIKE = "LIKE";
    public static final String COMMENT = "COMMENT";
    public static final String FRIEND = "FRIEND";

    public static final String REQUEST = "REQUEST";
    public static final String REQUEST_SENT = "REQUEST_SENT";
    public static final String ACCEPT = "ACCEPT";
    public static final String REJECT = "REJECT";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private long fromUserId;
    private long postId;
    private String type;
    private boolean isRead;
    private String action;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
