package ogjg.instagram.like.domain.innerCommentLike;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.like.dto.innerCommentLike.InnerCommentLikeDto;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InnerCommentLikePK implements Serializable {
    private Long userId;
    private Long innerCommentId;

    public InnerCommentLikePK(InnerCommentLikeDto innerCommentLikeDto) {
        this.userId = innerCommentLikeDto.getUserId();
        this.innerCommentId = innerCommentLikeDto.getInnerCommentId();
    }
}
