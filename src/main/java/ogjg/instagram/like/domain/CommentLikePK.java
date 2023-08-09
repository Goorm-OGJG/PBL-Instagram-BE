package ogjg.instagram.like.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class CommentLikePK implements Serializable {
    private Long userId;
    private Long commentId;
}
