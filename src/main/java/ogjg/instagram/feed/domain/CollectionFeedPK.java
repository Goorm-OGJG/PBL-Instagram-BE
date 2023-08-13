package ogjg.instagram.feed.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class CollectionFeedPK implements Serializable {
    private Long feedId;
    private Long collectionId;
    private Long userId;

    public CollectionFeedPK(Long feedId, Long collectionId, Long userId) {
        this.feedId = feedId;
        this.collectionId = collectionId;
        this.userId = userId;
    }
}
