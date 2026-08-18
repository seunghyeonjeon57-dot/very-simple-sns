package com.seunghyeon.verysimplesns.dto.response;




import com.seunghyeon.verysimplesns.domain.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String nickName

) {

    public static UserResponse from(User user){
        return new UserResponse(user.getId(),user.getEmail(), user.getNickName())
;    }
}
