package com.seunghyeon.verysimplesns.dto.response;

import com.seunghyeon.verysimplesns.domain.Follow;

import java.util.UUID;

public record FollowResponse(
        UUID followerId,
        UUID followingId
) {

    public static FollowResponse from(Follow follow){
        return new FollowResponse(
                follow.getFollower().getId(),
                follow.getFollowing().getId()
        );
    }
}
