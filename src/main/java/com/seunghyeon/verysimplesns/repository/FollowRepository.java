package com.seunghyeon.verysimplesns.repository;

import com.seunghyeon.verysimplesns.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {
    List<Follow> findByFollowerId(UUID followerId);
    List<Follow> findByFollowingId(UUID followingId);
    boolean existsByFollowerIdAndFollowingId(UUID followerId,UUID FollowingId);
    void deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);



}
