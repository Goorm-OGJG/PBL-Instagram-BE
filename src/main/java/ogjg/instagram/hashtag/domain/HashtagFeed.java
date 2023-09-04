package ogjg.instagram.hashtag.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class HashtagFeed {

    @EmbeddedId
    private HashtagPK hashtagPK;

    @MapsId("hashtagId")
    @ManyToOne(fetch = LAZY, cascade = REMOVE)
    private Hashtag hashtag;

    @MapsId("feedId")
    @ManyToOne(fetch = LAZY)
    private Feed feed;

    @Builder
    public HashtagFeed(HashtagPK hashtagPK, Hashtag hashtag, Feed feed) {
        this.hashtagPK = hashtagPK;
        this.hashtag = hashtag;
        this.feed = feed;
    }
}
