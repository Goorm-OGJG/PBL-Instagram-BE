package ogjg.instagram.follow.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FollowPK implements Serializable {
    private Long userId;
    private Long followId;
}