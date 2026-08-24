package com.seunghyeon.verysimplesns.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record FeedCreatedRequest(

        @Size(max=255,message = "피드는 255자 제한이 걸려있습니다.")
        @NotBlank
        String content,


        String imageUrl

        ) {
}
