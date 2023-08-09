package ogjg.instagram.feed.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.like.domain.FeedLike;
import ogjg.instagram.user.domain.CollectionFeed;
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
public class Feed {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "feed")
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<CollectionFeed> collectionFeeds = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<FeedMedia> feedMedias = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<Comment> comments = new ArrayList<>();

    @Column(length = 2200, nullable = false)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
