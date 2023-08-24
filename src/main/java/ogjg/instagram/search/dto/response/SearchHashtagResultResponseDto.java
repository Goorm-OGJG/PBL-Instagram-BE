package ogjg.instagram.search.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;

import java.util.List;

@NoArgsConstructor
@Getter
public class SearchHashtagResultResponseDto {
    private String tagName;
    private Long feedCount;
    private String thumbnail;
    private List<SearchHashtagResultDto> taggedList;

    public static SearchHashtagResultResponseDto from(List<Feed> feeds, Long feedCount, String content, String thumbnail) {
        return SearchHashtagResultResponseDto.builder()
                .taggedList(feeds.stream()
                        .map((SearchHashtagResultDto::from))
                        .toList())
                .tagName(content)
                .feedCount(feedCount)
                .thumbnail(thumbnail)
                .build();
    }

    @Builder
    public SearchHashtagResultResponseDto(String tagName, Long feedCount, String thumbnail, List<SearchHashtagResultDto> taggedList) {
        this.tagName = tagName;
        this.feedCount = feedCount;
        this.thumbnail = thumbnail;
        this.taggedList = taggedList;
    }

    @Getter
    private static class SearchHashtagResultDto {
        private Long feedId;
        private String mediaUrl;
        private boolean isMediaOne;

        @Builder
        public SearchHashtagResultDto(Long feedId, String mediaUrl, boolean isMediaOne) {
            this.feedId = feedId;
            this.mediaUrl = mediaUrl;
            this.isMediaOne = isMediaOne;
        }

        public static SearchHashtagResultDto from(Feed feed) {
            return SearchHashtagResultDto.builder()
                    .feedId(feed.getId())
                    .mediaUrl(feed.getFeedMedias().get(0).getMediaUrl())
                    .isMediaOne(feed.getFeedMedias().size() == 1)
                    .build();
        }
    }
}