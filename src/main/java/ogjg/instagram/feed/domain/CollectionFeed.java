package ogjg.instagram.feed.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class CollectionFeed {

    @EmbeddedId
    private CollectionFeedPK collectionFeedPK;

    @MapsId("feedId")
    @ManyToOne(fetch = LAZY)
    private Feed feed;

    @MapsId("collectionId")
    @ManyToOne(fetch = LAZY)
    private Collection collection;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @Builder
    public CollectionFeed(CollectionFeedPK collectionFeedPK, Feed feed, Collection collection, User user) {
        this.collectionFeedPK = collectionFeedPK;
        this.feed = feed;
        this.collection = collection;
        this.user = user;
    }

    public static CollectionFeed of(Feed feed, User user) {
        return CollectionFeed.builder()
                .collectionFeedPK(new CollectionFeedPK(feed.getId(), 0L, user.getId())) //todo: collectionId 상수처리
                .feed(feed)
                .user(user)
                .build();
    }
}
