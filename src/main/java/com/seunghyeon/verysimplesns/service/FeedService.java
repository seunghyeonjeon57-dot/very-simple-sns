package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.domain.Feed;
import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.FeedCreatedRequest;
import com.seunghyeon.verysimplesns.dto.request.FeedUpdatedRequest;
import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import com.seunghyeon.verysimplesns.repository.FeedRepository;
import com.seunghyeon.verysimplesns.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    public Feed create(UUID userId,FeedCreatedRequest request){

        User user  = userRepository.findById(userId)
                .orElseThrow(()-> new SimpleSnsException("유저를 찾을 수 없습니다.",HttpStatus.NOT_FOUND));

       Feed feed= Feed.builder()
                .user(user).
        content(request.content()).imageUrl(request.imageUrl()).build();
       return feedRepository.save(feed);
    }

    @Transactional(readOnly=true)
    public List<Feed> find(UUID userId, Instant cursor, Pageable pageable){
        if(cursor == null)
        {
            cursor = Instant.now();
        }
        return feedRepository.findByUserId(userId,cursor,pageable);
    }

    public Feed update(UUID id,UUID userId, FeedUpdatedRequest request){
        Feed feed=getOwnedFeed(id,userId);
        feed.updateFeed(request.content(),request.imageUrl());

       return feed;
    }

    public void delete(UUID id,UUID userId){
        Feed feed=getOwnedFeed(id,userId);
        feedRepository.delete(feed);
    }


    private Feed getOwnedFeed(UUID id,UUID userId){
        Feed feed  = feedRepository.findById(id)
                .orElseThrow(()->new SimpleSnsException("게시물을 찾을 수 없습니다.",HttpStatus.NOT_FOUND));
        if(!feed.getUser().getId().equals(userId)){
            throw new SimpleSnsException("게시물의 유저가 아닙니다.",HttpStatus.NOT_FOUND);
        }
        return feed;
    }


}
