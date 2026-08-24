package com.seunghyeon.verysimplesns.controller;


import com.seunghyeon.verysimplesns.domain.Feed;
import com.seunghyeon.verysimplesns.dto.request.FeedCreatedRequest;
import com.seunghyeon.verysimplesns.dto.request.FeedUpdatedRequest;
import com.seunghyeon.verysimplesns.dto.response.FeedResponse;
import com.seunghyeon.verysimplesns.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;


    @PostMapping("/create")
    public ResponseEntity<FeedResponse> create(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody FeedCreatedRequest request
            ){
        Feed feed = feedService.create(userId,request);
        FeedResponse response = new FeedResponse(feed.getContent(),feed.getImageUrl());
        URI location = URI.create("/feed/create" + feed.getId());
        return ResponseEntity.created(location).body(response);

    }

    @GetMapping("/find")
    public ResponseEntity<List<FeedResponse>> find(
            @AuthenticationPrincipal UUID userId,
            @RequestParam Instant cursor,
             Pageable pageable
    ){
       List<Feed> feeds = feedService.find(userId, cursor,pageable);
        List<FeedResponse> response  = feeds.stream().map(f-> new FeedResponse(f.getContent(),f.getImageUrl()))
               .toList();

        return ResponseEntity.ok(response);
    }



    @PostMapping("update/{id}")
    public ResponseEntity<FeedResponse> update(
            @PathVariable UUID id,
            @AuthenticationPrincipal UUID userId,
            @RequestBody FeedUpdatedRequest request
            ){
        Feed feed = feedService.update(id,userId,request);
        FeedResponse response = new FeedResponse(feed.getContent(),feed.getImageUrl());
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UUID userId

    ){
        feedService.delete(id,userId);
        return ResponseEntity.noContent().build();
    }
}
