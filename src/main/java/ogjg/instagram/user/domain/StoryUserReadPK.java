package ogjg.instagram.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ogjg.instagram.story.dto.request.StoryUserReadDto;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryUserReadPK implements Serializable {
    private Long userId;
    private Long storyId;

    public StoryUserReadPK(StoryUserReadDto storyUserReadDto) {
        this.userId = storyUserReadDto.getUserId();
        this.storyId = storyUserReadDto.getStoryId();
    }
}
