package ogjg.instagram.user.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StoryUserReadPK implements Serializable {
    private Long userId;
    private Long storyId;
}
