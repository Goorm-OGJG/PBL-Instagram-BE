package ogjg.instagram.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.follow.domain.Follow;
import ogjg.instagram.like.domain.CommentLike;
import ogjg.instagram.like.domain.FeedLike;
import ogjg.instagram.like.domain.InnerCommentLike;
import ogjg.instagram.like.domain.StoryLike;
import ogjg.instagram.story.domain.Story;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String userIntro;

    @Column(length = 2200)
    private String userImg;

    private boolean recommend;

    private boolean secret;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user")
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Collection> collections = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<InnerComment> innerComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<InnerCommentLike> innerCommentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StoryLike> storyLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StoryUserRead> storyUserReads = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Follow> follows = new ArrayList<>();
}