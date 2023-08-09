package ogjg.instagram.comment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.like.domain.CommentLike;
import ogjg.instagram.user.domain.User;
import org.hibernate.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Feed feed;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private List<InnerComment> innerComments = new ArrayList<>();

    @Column(length = 2200, nullable = false)
    private String content;

    private LocalDateTime createdAt;
}
