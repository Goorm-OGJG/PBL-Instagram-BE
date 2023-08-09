package ogjg.instagram.like.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class InnerCommentLike {

    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @EmbeddedId
    private InnerCommentLikePK innerCommentLikePK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("innerCommentId")
    @ManyToOne(fetch = LAZY)
    private InnerComment innerComment;

    private LocalDateTime createdAt;
}
