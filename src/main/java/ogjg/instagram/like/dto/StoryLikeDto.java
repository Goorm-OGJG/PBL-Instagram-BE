package ogjg.instagram.like.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class StoryLikeDto {

    private Long userId;
    private Long storyId;
    private LocalDateTime createdAt;

    public StoryLikeDto(Long userId, Long storyId) {
        this.userId = userId;
        this.storyId = storyId;
        this.createdAt = LocalDateTime.now();
    }
}
