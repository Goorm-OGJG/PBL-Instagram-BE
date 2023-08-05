package ogjg.instagram.user.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CollectionFeedPK implements Serializable {
    private Long feedId;
    private Long collectionId;
    private Long userId;
}
