package ogjg.instagram.like.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class InnerCommentLikePK implements Serializable {
    private Long userId;
    private Long innerCommentId;
}
