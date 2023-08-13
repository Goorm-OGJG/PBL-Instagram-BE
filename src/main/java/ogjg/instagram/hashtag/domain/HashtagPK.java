package ogjg.instagram.hashtag.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class HashtagPK implements Serializable {
    private Long hashtagId;
    private Long feedId;

    public HashtagPK(Long hashtagId, Long feedId) {
        this.hashtagId = hashtagId;
        this.feedId = feedId;
    }
}
