package ogjg.instagram.story.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ogjg.instagram.story.domain.Story;

import java.time.LocalDateTime;

@Getter @Setter
public class StoryMediaSaveDto {

    private Story story;
    private String mediaType;
    private String mediaUrl;

    @Builder
    public StoryMediaSaveDto(Story story, String mediaType, String mediaUrl) {
        this.story = story;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
    }
}
