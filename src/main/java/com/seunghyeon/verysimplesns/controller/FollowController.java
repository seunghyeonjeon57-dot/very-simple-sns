package com.seunghyeon.verysimplesns.controller;

import com.seunghyeon.verysimplesns.domain.Follow;
import com.seunghyeon.verysimplesns.dto.response.FollowResponse;
import com.seunghyeon.verysimplesns.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService service;

    @PostMapping("/{followingId}")
    public ResponseEntity<FollowResponse> follow(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID followingId
    ){
        Follow follow = service.follow(userId,followingId);
        FollowResponse response = FollowResponse.from(follow);
        return ResponseEntity.status(201).body(response);
    }



    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> unFollow(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID followingId
    ){
        service.unfollow(userId, followingId);
        return ResponseEntity.noContent().build();
    }
}
