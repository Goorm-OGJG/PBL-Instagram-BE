package ogjg.instagram.like.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FeedLikePK implements Serializable {
    private Long userId;
    private Long feedId;
}
