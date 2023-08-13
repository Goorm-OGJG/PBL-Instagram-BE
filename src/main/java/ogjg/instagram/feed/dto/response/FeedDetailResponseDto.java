package ogjg.instagram.feed.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
public class FeedDetailResponseDto {

    private Long userId;
    private String userImg;
    private String content;
    private LocalDateTime createdAt;
    private String nickname;

    private Long feedId;
    private Long likeCount;
    private boolean likeStatus;
    private boolean collectionStatus;

    private List<FeedMediaResponseDto> feedMedias;
    private List<CommentThumbnailDto> comments;


    public static FeedDetailResponseDto from(Feed feed, Long likeCount, boolean likeStatus, boolean collectionStatus, List<CommentThumbnailDto> commentThumbnailDto) {

        User user = feed.getUser();

        return FeedDetailResponseDto.builder()
                .feedId(feed.getId())
                .content(feed.getContent())
                .createdAt(feed.getCreatedAt())

                .userId(user.getId())
                .nickname(user.getNickname())
                .userImg(user.getUserImg())

                .feedMedias(feed.getFeedMedias().stream()
                        .map(f -> new FeedMediaResponseDto(f))
                        .collect(toList()))

                .comments(commentThumbnailDto)

                .likeCount(likeCount)
                .likeStatus(likeStatus)
                .collectionStatus(collectionStatus).build();
    }

    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class CommentThumbnailDto {

        private Long commentId;
        private Long userId;
        private String nickname;
        private String userImg;
        private String content;
        private Long likeCount;
        private boolean likeStatus;
        private LocalDateTime createdAt;
        private Long innerCommentCount;

        public CommentThumbnailDto(Comment comment, User user, Long likeCount, boolean innerLikeStatus, Long innerCommentCount) {
            this.commentId = comment.getId();
            this.userId = user.getId();
            this.nickname = user.getNickname();
            this.userImg = user.getUserImg();
            this.content = comment.getContent();
            this.likeCount = likeCount;
            this.likeStatus = innerLikeStatus;
            this.innerCommentCount = innerCommentCount;
            this.createdAt = comment.getCreatedAt();
        }
    }
}
