package ogjg.instagram.like.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class InnerCommentLikePK implements Serializable {
    private Long userId;
    private Long innerCommentId;
}
