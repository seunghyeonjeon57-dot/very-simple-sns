package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.domain.Feed;
import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.FeedCreatedRequest;
import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import com.seunghyeon.verysimplesns.repository.FeedRepository;
import com.seunghyeon.verysimplesns.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    public Feed create(FeedCreatedRequest request){

        User user  = userRepository.findById(request.userId())
                .orElseThrow(()-> new SimpleSnsException("유저를 찾을 수 없습니다.",HttpStatus.NOT_FOUND));

        Feed feed = Feed.builder()
                .user(user).
        content(request.content()).imageUrl(request.imageUrl()).build();
        return feedRepository.save(feed);
    }

    public List<Feed> find(UUID userId, Instant cursor, Pageable pageable){
        return List.of();
    }

}
