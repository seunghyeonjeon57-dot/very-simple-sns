package com.seunghyeon.verysimplesns.repository;

import com.seunghyeon.verysimplesns.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    @Query("SELECT f FROM Follow f JOIN fetch f.follower WHERE f.follower.id = :followerId AND f.createdAt < :cursor ORDER BY f.createdAt DESC")
    List<Follow> findByFollowerId(@Param("followerId") UUID followerId, @Param("cursor") Instant cursor, Pageable pageable);

    @Query("SELECT f FROM Follow f JOIN fetch f.following WHERE f.following.id = :followingId AND f.createdAt < :cursor ORDER BY f.createdAt DESC")
    List<Follow> findByFollowingId(@Param("followingId") UUID followingId,@Param("cursor") Instant cursor,Pageable pageable);
    boolean existsByFollowerIdAndFollowingId(UUID followerId,UUID FollowingId);
    void deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);



}
