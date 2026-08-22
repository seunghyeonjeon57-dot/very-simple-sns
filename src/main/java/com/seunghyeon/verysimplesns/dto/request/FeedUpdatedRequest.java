package com.seunghyeon.verysimplesns.dto.request;

import jakarta.validation.constraints.Size;

public record FeedUpdatedRequest(
        @Size(max=255,message = "피드는 255자 제한이 걸려있습니다.")
        String content,

        String imageUrl
) {
}
