package ogjg.instagram.like.domain.feedLike;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ogjg.instagram.like.dto.feedLike.FeedLikeDto;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLikePK implements Serializable {
    private Long userId;
    private Long feedId;

    public FeedLikePK(FeedLikeDto feedLikeDto) {
        this.userId = feedLikeDto.getUserId();
        this.feedId = feedLikeDto.getFeedId();
    }
}
