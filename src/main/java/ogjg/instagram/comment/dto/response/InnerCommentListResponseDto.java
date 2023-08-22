package ogjg.instagram.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class InnerCommentListResponseDto {
    private Long commentId;
    private List<InnerCommentListDto> innerComments;


    @Builder
    public InnerCommentListResponseDto(Long commentId, List<InnerCommentListDto> innerComments) {
        this.commentId = commentId;
        this.innerComments = innerComments;
    }

    @Getter
    public static class InnerCommentListDto {

        private Long innerCommentId;
        private InnerCommentWriter innerCommentWriter;
        private String content;
        private boolean likeStatus;
        private Long likeCount;
        private LocalDateTime createdAt;

        @Builder
        public InnerCommentListDto(User user, Long innerCommentId, String content, boolean likeStatus, Long likeCount, LocalDateTime createdAt) {
            this.innerCommentWriter = new InnerCommentWriter(user);
            this.innerCommentId = innerCommentId;
            this.content = content;
            this.likeStatus = likeStatus;
            this.likeCount = likeCount;
            this.createdAt = createdAt;
        }

        public static InnerCommentListDto from(InnerComment innerComment, boolean likeStatus, Long likeCount) {
            return InnerCommentListDto.builder()
                    .innerCommentId(innerComment.getId())
                    .user(innerComment.getUser())
                    .content(innerComment.getContent())
                    .likeStatus(likeStatus)
                    .likeCount(likeCount)
                    .createdAt(innerComment.getCreatedAt())
                    .build();
        }

        @Getter
        static class InnerCommentWriter {
            private Long userId;
            private String nickname;
            private String userImg;

            public InnerCommentWriter(User user) {
                this.userId = user.getId();
                this.nickname = user.getNickname();
                this.userImg = user.getUserImg();
            }
        }
    }
}
