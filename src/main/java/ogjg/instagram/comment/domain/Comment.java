package ogjg.instagram.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.like.domain.commentLike.CommentLike;
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

    @Builder
    public Comment(Long id, Feed feed, User user, List<CommentLike> commentLikes, List<InnerComment> innerComments, String content, LocalDateTime createdAt) {
        this.id = id;
        this.feed = feed;
        this.user = user;
        this.commentLikes = commentLikes;
        this.innerComments = innerComments;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Comment from(User user, Feed feed, String content) {
        return Comment.builder()
                .user(user)
                .feed(feed)
                .content(content)
                .build();
    }
}
