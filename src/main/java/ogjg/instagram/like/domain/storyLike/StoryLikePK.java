package ogjg.instagram.like.domain.storyLike;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ogjg.instagram.like.dto.StoryLikeDto;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryLikePK implements Serializable {
    private Long userId;
    private Long storyId;

    public StoryLikePK(StoryLikeDto storyLikeDto) {
        this.userId = storyLikeDto.getUserId();
        this.storyId = storyLikeDto.getStoryId();
    }
}
