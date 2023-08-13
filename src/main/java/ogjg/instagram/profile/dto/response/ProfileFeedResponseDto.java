package ogjg.instagram.profile.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.domain.CollectionFeed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class ProfileFeedResponseDto {
    private List<ProfileFeedDto> feedList;
    private boolean isLast;

    protected ProfileFeedResponseDto(List<ProfileFeedDto> feedList) {
        this.feedList = feedList;
    }

    public static ProfileFeedResponseDto from(Page<ProfileFeedDto> feedPage) {
        List<ProfileFeedDto> feedList = feedPage.getContent().stream()
        .collect(Collectors.toList());

        ProfileFeedResponseDto responseDto = new ProfileFeedResponseDto(feedList);

        responseDto.isLast = feedPage.isLast();
        return responseDto;
    }

    public static ProfileFeedResponseDto fromCollected(Page<CollectionFeed> collectedFeedPage) {
        List<ProfileFeedDto> feedList = collectedFeedPage.getContent()
                .stream()
                .map((collectedFeed) -> new ProfileFeedDto(collectedFeed))
                .collect(Collectors.toUnmodifiableList());

        ProfileFeedResponseDto responseDto = new ProfileFeedResponseDto(feedList);

        responseDto.isLast = collectedFeedPage.isLast();
        return responseDto;
    }

    @Builder
    @Getter
    @Setter
    public static class ProfileFeedDto {
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

        public ProfileFeedDto(CollectionFeed collectedFeed) {
            Feed feed = collectedFeed.getFeed();

            //todo: 빈리스트, 비효율적인 객체 그래프 탐색 수정
            // 매번 feed_id 마다 comment의 count, like의 count를 검색을 해야하는데, 페이징과 fetch를 어떻게 구현하지?
            this.feedId = feed.getId();
            this.mediaUrl = feed.getFeedMedias().get(0).getMediaUrl();
            this.isMediaOne = feed.getFeedMedias().size() == 1;
            this.likeCount = Long.valueOf(feed.getFeedLikes().size());
            this.commentCount = Long.valueOf(feed.getComments().size());
        }

    }
}