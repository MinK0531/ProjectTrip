package com.mink.projecttrip.friend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "`friend_request`")
public class FriendRequest {

    public static final int WAITING = 0;
    public static final int ACCEPTED = 1;
    public static final int REJECTED = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long fromUserId;
    private long toUserId;
    private int status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void accept() {
        this.status = ACCEPTED;
    }
    public void reject() {
        this.status = REJECTED;
    }
}
