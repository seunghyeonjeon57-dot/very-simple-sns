package com.seunghyeon.verysimplesns.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FeedResponse(

        @NotBlank
        @Size(max =255 , message = "피드느 255자가 최대입니다.")
        String content,
        String imageUrl

) {
}
