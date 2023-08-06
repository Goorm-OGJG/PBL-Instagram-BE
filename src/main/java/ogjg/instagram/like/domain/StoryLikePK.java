package ogjg.instagram.like.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StoryLikePK implements Serializable {
    private Long userId;
    private Long storyId;
}
