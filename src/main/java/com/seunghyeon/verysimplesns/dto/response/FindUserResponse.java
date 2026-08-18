package com.seunghyeon.verysimplesns.dto.response;

import com.seunghyeon.verysimplesns.domain.User;

import java.util.UUID;

public record FindUserResponse(
        UUID userId,
        String nickName
) {
    public static FindUserResponse from(User user){
        return new FindUserResponse(user.getId(),user.getNickName());
    }
}
