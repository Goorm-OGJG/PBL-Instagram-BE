package ogjg.instagram.feed.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FeedListResponseDto {
    private List<FeedListDto> contents;
    private boolean isLast;

    public FeedListResponseDto(List<FeedListDto> contents, boolean isLast) {
        this.contents = contents;
        this.isLast = isLast;
    }

    public static FeedListResponseDto from(List<FeedListDto> contents, boolean isLast) {
        return new FeedListResponseDto(contents, isLast);
    }

    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class FeedListDto {

        private Long userId;
        private String userImg;
        private String nickname;
        private Long feedId;
        private String content;
        private LocalDateTime createdAt;
        private Long likeCount;
        private boolean likeStatus;
        private boolean collectionStatus;

        private List<FeedMediaResponseDto> feedMedias;

        @Builder
        public FeedListDto(User user, Feed feed, Long likeCount, boolean likeStatus, boolean collectionStatus, List<FeedMediaResponseDto> feedMedias) {
            this.userId = user.getId();
            this.userImg = user.getUserImg();
            this.nickname = user.getNickname();
            this.feedId = feed.getId();
            this.content = feed.getContent();
            this.createdAt = feed.getCreatedAt();
            this.likeCount = likeCount;
            this.likeStatus = likeStatus;
            this.collectionStatus = collectionStatus;
            this.feedMedias = feedMedias;
        }
    }
}
