package ogjg.instagram.comment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.like.domain.InnerCommentLike;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class InnerComment {

    @Id
    @Column(name = "inner_comment_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Comment comment;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "innerComment")
    private List<InnerCommentLike> innerCommentLikes = new ArrayList<>();

    private String content;

    private LocalDateTime createdAt;
}
