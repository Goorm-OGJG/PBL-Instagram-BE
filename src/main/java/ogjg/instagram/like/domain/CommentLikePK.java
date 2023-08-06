package ogjg.instagram.like.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CommentLikePK implements Serializable {
    private Long userId;
    private Long commentId;
}
