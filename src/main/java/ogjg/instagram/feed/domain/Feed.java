package ogjg.instagram.feed.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.hashtag.domain.HashtagFeed;
import ogjg.instagram.like.domain.feedLike.FeedLike;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Feed {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "feed", cascade = REMOVE, orphanRemoval = true)
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = REMOVE, orphanRemoval = true)
    private List<CollectionFeed> collectionFeeds = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = REMOVE, orphanRemoval = true)
    private List<HashtagFeed> hashtagFeeds = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = REMOVE, orphanRemoval = true)
    private List<FeedMedia> feedMedias = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(length = 2200, nullable = false)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    @Builder
    public Feed(Long id, User user, List<FeedLike> feedLikes, List<CollectionFeed> collectionFeeds, List<HashtagFeed> hashtagFeeds, List<FeedMedia> feedMedias, List<Comment> comments, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.user = user;
        this.feedLikes = feedLikes;
        this.collectionFeeds = collectionFeeds;
        this.hashtagFeeds = hashtagFeeds;
        this.feedMedias = feedMedias;
        this.comments = comments;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void addAllMedias(List<FeedMedia> medias) {
        this.feedMedias = medias;
    }
}
