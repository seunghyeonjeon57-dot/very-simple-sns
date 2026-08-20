package com.seunghyeon.verysimplesns.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "idx_feed_user_created",columnList = "user_id, created_at")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false,length = 255)
    private String content;

    @Column
    private String imageUrl;

    @CreatedDate
    @Column
    private Instant createdAt;

    @LastModifiedDate
    @Column
    private Instant updatedAt;


    public void updateFeed(String newContent, String newImageURl){
        if(newContent!=null) this.content=newContent;
        if(newImageURl!=null) this.imageUrl=newImageURl;
    }


}
