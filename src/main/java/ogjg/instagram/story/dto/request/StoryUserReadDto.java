package ogjg.instagram.story.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class StoryUserReadDto {

    private Long storyId;
    private Long userId;

    public StoryUserReadDto(Long storyId, Long userId) {
        this.storyId = storyId;
        this.userId = userId;
    }
}
