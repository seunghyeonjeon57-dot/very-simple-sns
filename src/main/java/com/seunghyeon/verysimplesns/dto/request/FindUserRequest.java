package com.seunghyeon.verysimplesns.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record FindUserRequest(
        @NotBlank
        String userName
) {
}
