package ogjg.instagram.user.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class StoryUserReadPK implements Serializable {
    private Long userId;
    private Long storyId;
}
