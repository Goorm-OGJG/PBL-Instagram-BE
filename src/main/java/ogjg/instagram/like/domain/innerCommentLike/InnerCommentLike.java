package ogjg.instagram.like.domain.innerCommentLike;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.like.dto.innerCommentLike.InnerCommentLikeDto;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class InnerCommentLike {

    @EmbeddedId
    private InnerCommentLikePK innerCommentLikePK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("innerCommentId")
    @ManyToOne(fetch = LAZY)
    private InnerComment innerComment;

    private LocalDateTime createdAt;


    public InnerCommentLike(InnerCommentLikeDto innerCommentLikeDto, User user, InnerComment innerComment) {
        this.innerCommentLikePK = new InnerCommentLikePK(innerCommentLikeDto);
        this.user = user;
        this.innerComment = innerComment;
        this.createdAt = LocalDateTime.now();
    }
}
