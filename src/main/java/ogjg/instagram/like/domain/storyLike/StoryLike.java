package ogjg.instagram.like.domain.storyLike;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.like.dto.StoryLikeDto;
import ogjg.instagram.story.domain.Story;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class StoryLike {

    @EmbeddedId
    private StoryLikePK storyLikePK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("storyId")
    @ManyToOne(fetch = LAZY)
    private Story story;

    private LocalDateTime createdAt;


    public StoryLike(StoryLikeDto storyLikeDto, User user, Story story) {
        this.storyLikePK = new StoryLikePK(storyLikeDto);
        this.user = user;
        this.story = story;
        this.createdAt = storyLikeDto.getCreatedAt();
    }
}
