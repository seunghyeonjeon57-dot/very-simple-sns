package com.seunghyeon.verysimplesns.dto.response;

import java.util.UUID;

public record SignUpResponse(
        UUID id,
        String email,
        String nickName

) {
}
