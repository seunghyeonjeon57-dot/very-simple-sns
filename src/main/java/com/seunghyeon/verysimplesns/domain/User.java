package com.seunghyeon.verysimplesns.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Table
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    private UUID id;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    private String nickname;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column
    private Instant updatedAt;






}
