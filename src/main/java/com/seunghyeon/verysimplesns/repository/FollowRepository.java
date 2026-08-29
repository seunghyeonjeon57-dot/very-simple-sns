package com.seunghyeon.verysimplesns.repository;

import com.seunghyeon.verysimplesns.domain.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    @Query("SELECT f FROM Follow f JOIN fetch f.following WHERE f.follower.id = :followerId AND f.createdAt < :cursor ORDER BY f.createdAt DESC")
    List<Follow> findByFollowerId(@Param("followerId") UUID followerId, @Param("cursor") Instant cursor, Pageable pageable);

    @Query("SELECT f FROM Follow f JOIN fetch f.follower WHERE f.following.id = :followingId AND f.createdAt < :cursor ORDER BY f.createdAt DESC")
    List<Follow> findByFollowingId(@Param("followingId") UUID followingId,@Param("cursor") Instant cursor,Pageable pageable);
    boolean existsByFollowerIdAndFollowingId(UUID followerId,UUID FollowingId);
    void deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);

    @Query("SELECT f.following.id FROM Follow f WHERE f.follower.id  = :userId AND f.following.id IN (:ids)")
    List<UUID> findMutualForFollowerList(@Param("userId") UUID userId, @Param("ids") List<UUID> ids);

    @Query("SELECT f.follower.id FROM Follow f WHERE f.following.id = :userId AND f.follower.id IN (:ids)")
    List<UUID> findMutualForFollowingList(@Param("userId") UUID userId , @Param("ids") List<UUID> ids);




}
