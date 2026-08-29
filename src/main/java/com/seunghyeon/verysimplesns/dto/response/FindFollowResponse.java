package com.seunghyeon.verysimplesns.dto.response;

import com.seunghyeon.verysimplesns.domain.Follow;

import java.util.UUID;

public record FindFollowResponse(
        UUID userId,
        String nickName
) {
    public static FindFollowResponse fromFollower(Follow f){
        return new FindFollowResponse(
                f.getFollower().getId(),
                f.getFollower().getNickName()
        );
    }

    public static FindFollowResponse fromFollowing(Follow f){
        return new FindFollowResponse(
                f.getFollowing().getId(),
                f.getFollowing().getNickName()
        );
    }
}
