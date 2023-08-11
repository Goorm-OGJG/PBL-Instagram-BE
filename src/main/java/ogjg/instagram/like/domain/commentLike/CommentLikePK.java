package ogjg.instagram.like.domain.commentLike;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ogjg.instagram.like.dto.commentLike.CommentLikeDto;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikePK implements Serializable {
    private Long userId;
    private Long commentId;

    public CommentLikePK(CommentLikeDto commentLikeDto) {
        this.userId = commentLikeDto.getUserId();
        this.commentId = commentLikeDto.getCommentId();
    }
}
