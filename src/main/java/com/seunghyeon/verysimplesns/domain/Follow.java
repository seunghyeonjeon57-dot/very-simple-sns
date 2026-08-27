package com.seunghyeon.verysimplesns.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Table(name = "Follow",
uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_follow_follower_follwing",
                columnNames = {"follower_id","following_id"}

        )
},indexes = {@Index(name = "idx_follow_follower",columnList = "follower_id"),
            @Index(name= "idx_follow_following",columnList = "following_id")})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id",nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;


    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;




}
