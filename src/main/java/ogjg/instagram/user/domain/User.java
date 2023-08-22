package ogjg.instagram.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.feed.domain.Collection;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.follow.domain.Follow;
import ogjg.instagram.like.domain.commentLike.CommentLike;
import ogjg.instagram.like.domain.feedLike.FeedLike;
import ogjg.instagram.like.domain.innerCommentLike.InnerCommentLike;
import ogjg.instagram.like.domain.storyLike.StoryLike;
import ogjg.instagram.profile.dto.request.ProfileEditRequestDto;
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

    public User(Long id, String nickname, String userName, String email, String password, String userIntro, String userImg, boolean recommend, boolean secret, boolean isActive) {
        this.id = id;
        this.nickname = nickname;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userIntro = userIntro;
        this.userImg = userImg;
        this.recommend = recommend;
        this.secret = secret;
        this.isActive = isActive;
    }

    @Builder
    public User(Long id, String nickname, String userName, String email, String password, String userIntro, String userImg, boolean recommend, boolean secret, boolean isActive, LocalDateTime createdAt, LocalDateTime modifiedAt, List<FeedLike> feedLikes, List<Feed> feeds, List<Collection> collections, List<Comment> comments, List<CommentLike> commentLikes, List<InnerComment> innerComments, List<InnerCommentLike> innerCommentLikes, List<Story> stories, List<StoryLike> storyLikes, List<StoryUserRead> storyUserReads, List<Follow> follows) {
        this.id = id;
        this.nickname = nickname;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userIntro = userIntro;
        this.userImg = userImg;
        this.recommend = recommend;
        this.secret = secret;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.feedLikes = feedLikes;
        this.feeds = feeds;
        this.collections = collections;
        this.comments = comments;
        this.commentLikes = commentLikes;
        this.innerComments = innerComments;
        this.innerCommentLikes = innerCommentLikes;
        this.stories = stories;
        this.storyLikes = storyLikes;
        this.storyUserReads = storyUserReads;
        this.follows = follows;
    }

    public static User reference(Long loginId) {
        User user = new User();
        user.id = loginId;
        return user;
    }

    public void editProfileImg(String imgUrl) {
        this.userImg = imgUrl;
    }

    public void editProfile(ProfileEditRequestDto requestDto) {
        this.userIntro = requestDto.getUserIntro();
        this.recommend = requestDto.isRecommended();
        this.secret = requestDto.isSecret();
    }
}