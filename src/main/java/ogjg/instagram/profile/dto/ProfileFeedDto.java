package ogjg.instagram.profile.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.user.domain.CollectionFeed;

@Builder
@Getter
@Setter
public class ProfileFeedDto {
    private Long feedId;
    private String mediaUrl;
    private boolean isMediaOne;
    private Long likeCount;
    private Long commentCount;

    public ProfileFeedDto(Long feedId, String mediaUrl, boolean isMediaOne, Long likeCount, Long commentCount) {
        this.feedId = feedId;
        this.mediaUrl = mediaUrl;
        this.isMediaOne = isMediaOne;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public ProfileFeedDto(CollectionFeed savedFeed) {
        Feed feed = savedFeed.getFeed();

        //todo: 빈리스트, 비효율적인 객체 그래프 탐색 수정
        // 매번 feed_id 마다 comment의 count, like의 count를 검색을 해야하는데, 페이징과 fetch를 어떻게 구현하지?
        this.feedId = feed.getId();
        this.mediaUrl = feed.getFeedMedias().get(0).getMediaUrl();
        this.isMediaOne = feed.getFeedMedias().size() == 1;
        this.likeCount = Long.valueOf(feed.getFeedLikes().size());
        this.commentCount = Long.valueOf(feed.getComments().size());
    }

}

