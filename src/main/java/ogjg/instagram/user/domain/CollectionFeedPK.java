package ogjg.instagram.user.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class CollectionFeedPK implements Serializable {
    private Long feedId;
    private Long collectionId;
    private Long userId;
}
