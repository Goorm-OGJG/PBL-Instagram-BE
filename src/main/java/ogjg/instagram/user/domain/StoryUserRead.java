package ogjg.instagram.user.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.story.domain.Story;
import ogjg.instagram.story.dto.request.StoryUserReadDto;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class StoryUserRead {

    @EmbeddedId
    private StoryUserReadPK storyUserReadPK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("storyId")
    @ManyToOne(fetch = LAZY)
    private Story story;


    public StoryUserRead(StoryUserReadDto storyUserReadDto, User user, Story story) {
        this.storyUserReadPK = new StoryUserReadPK(storyUserReadDto);
        this.user = user;
        this.story = story;
    }
}
