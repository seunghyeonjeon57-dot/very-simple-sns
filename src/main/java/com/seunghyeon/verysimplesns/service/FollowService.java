package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.domain.Follow;
import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import com.seunghyeon.verysimplesns.repository.FollowRepository;
import com.seunghyeon.verysimplesns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository repository;
    private final UserRepository userRepository;


    public Follow follow(UUID followerId,UUID followingId){
        User followUser = userRepository.findById(followerId)
                .orElseThrow(()-> new SimpleSnsException("존재하지 않는 유저입니다.",HttpStatus.NOT_FOUND));
        User followingUser = userRepository.findById(followingId)
                .orElseThrow(()->new SimpleSnsException("존재하지 않는 유저입니다.",HttpStatus.NOT_FOUND));

        if(followerId.equals(followingId)){
            throw new SimpleSnsException("자기 자신을 팔로우 할 수 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(repository.existsByFollowerIdAndFollowingId(followerId,followingId)){
            throw new SimpleSnsException("이미 팔로우한 대상입니다.",HttpStatus.CONFLICT);
        }
        return repository.save(Follow.builder().follower(followUser).following(followingUser).build());

    }

    @Transactional(readOnly = true)
    public List<Follow> getFollow(UUID userId){
        return repository.findByFollowerId(userId);
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowing(UUID userId){
        return repository.findByFollowingId(userId);
    }


    public void unfollow(UUID followerId, UUID followingId) {
        if (!repository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new SimpleSnsException(
                    "팔로우 관계가 존재하지 않습니다.",
                    HttpStatus.NOT_FOUND
            );
        }

        repository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }
}
