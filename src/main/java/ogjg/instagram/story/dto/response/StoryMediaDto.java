package ogjg.instagram.story.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class StoryMediaDto {

    private Long mediaId;
    private Long storyId;
    private String mediaType;
    private String mediaUrl;
    private LocalDateTime createdAt;
    private boolean likeStatus;

    public StoryMediaDto putLikeStatus(boolean likeStatus) {
        this.likeStatus = likeStatus;
        return this;
    }

    @Builder
    public StoryMediaDto(Long mediaId, Long storyId, String mediaType, String mediaUrl, LocalDateTime createdAt) {
        this.mediaId = mediaId;
        this.storyId = storyId;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.createdAt = createdAt;
    }
}
