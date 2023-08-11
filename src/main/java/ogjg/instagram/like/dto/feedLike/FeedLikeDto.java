package ogjg.instagram.like.dto.feedLike;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FeedLikeDto {

    private Long feedId;
    private Long userId;
    private LocalDateTime createdAt;

    public FeedLikeDto(Long feedId, Long userId) {
        this.feedId = feedId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
