package ogjg.instagram.like.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class FeedLike {

    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @EmbeddedId
    private FeedLikePK feedLikePK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("feedId")
    @ManyToOne(fetch = LAZY)
    private Feed feed;

    private LocalDateTime createdAt;
}
