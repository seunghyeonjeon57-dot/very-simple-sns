package com.seunghyeon.verysimplesns.controller;

import com.seunghyeon.verysimplesns.domain.Follow;
import com.seunghyeon.verysimplesns.dto.response.FindFollowResponse;
import com.seunghyeon.verysimplesns.dto.response.FollowResponse;
import com.seunghyeon.verysimplesns.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
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


    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FindFollowResponse>> getFollowing(
            @PathVariable UUID userId,
            @RequestParam(required = false) Instant cursor,
            Pageable pageable
    ){
       List<Follow> follow = service.getFollowings(userId,cursor,pageable);
        List<FindFollowResponse> responses =
                follow.stream()
                        .map(FindFollowResponse::fromFollowing
                        ).toList();
        return ResponseEntity.ok(responses);

    }

    @GetMapping("/{userId}/follower")
    public ResponseEntity<List<FindFollowResponse>> getFollower(
            @PathVariable UUID userId,
            @RequestParam(required = false) Instant cursor,
            Pageable pageable
    ){
        List<Follow> follows = service.getFollowers(userId, cursor, pageable);
        List<FindFollowResponse> response = follows.stream()
                .map(FindFollowResponse::fromFollower)
                .toList();

        return ResponseEntity.ok(response);

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
