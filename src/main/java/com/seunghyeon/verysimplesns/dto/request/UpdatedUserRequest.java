package com.seunghyeon.verysimplesns.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatedUserRequest(

        @Email
        String email,

        @Size(min=1,max=30)
        String nickName

) {
}
