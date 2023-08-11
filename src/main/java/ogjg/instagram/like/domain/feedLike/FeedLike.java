package ogjg.instagram.like.domain.feedLike;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.like.dto.feedLike.FeedLikeDto;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class FeedLike {

    @EmbeddedId
    private FeedLikePK feedLikePK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("feedId")
    @ManyToOne(fetch = LAZY)
    private Feed feed;

    private LocalDateTime createdAt;

    public FeedLike(FeedLikeDto feedLikeDto, User user, Feed feed) {
        this.feedLikePK = new FeedLikePK(feedLikeDto);
        this.user = user;
        this.feed = feed;
        this.createdAt = LocalDateTime.now();
    }
}
