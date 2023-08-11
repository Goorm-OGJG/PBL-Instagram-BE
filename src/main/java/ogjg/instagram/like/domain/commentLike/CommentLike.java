package ogjg.instagram.like.domain.commentLike;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.like.dto.commentLike.CommentLikeDto;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class CommentLike {

    @EmbeddedId
    private CommentLikePK commentLikePK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("commentId")
    @ManyToOne(fetch = LAZY)
    private Comment comment;

    private LocalDateTime createdAt;

    public CommentLike(CommentLikeDto commentLikeDto, User user, Comment comment) {
        this.commentLikePK = new CommentLikePK(commentLikeDto);
        this.user = user;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
}
