package ogjg.instagram.user.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;

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
}
