package com.seunghyeon.verysimplesns.repository;

import com.seunghyeon.verysimplesns.domain.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface FeedRepository extends JpaRepository<Feed,UUID> {
    @Query("SELECT f FROM Feed f WHERE f.createdAt <:cursor and f.user.id =:id  order by f.createdAt DESC")
    List<Feed> findByUserId(@Param("id") UUID id, @Param("cursor") Instant cursor, Pageable pageable);
}
