package ogjg.instagram.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InnerCommentListResponseDto {
    private Long commentId;
    private List<InnerCommentListDto> innerComments;

    @Builder
    public InnerCommentListResponseDto(Long commentId, List<InnerCommentListDto> innerComments) {
        this.commentId = commentId;
        this.innerComments = innerComments;
    }

    public static InnerCommentListResponseDto from(Long commentId, List<InnerComment> innerComments) {
        return InnerCommentListResponseDto.builder()
                .commentId(commentId)
                .innerComments(innerComments.stream()
                        .map((innerComment -> InnerCommentListDto.from(innerComment)))
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @Getter
    public static class InnerCommentListDto {

        private Long innerCommentId;
        private InnerCommentWriter innerCommentWriter;
        private String content;

        @Builder
        public InnerCommentListDto(User user, Long innerCommentId, String content) {
            this.innerCommentWriter = new InnerCommentWriter(user);
            this.innerCommentId = innerCommentId;
            this.content = content;
        }

        public static InnerCommentListDto from(InnerComment innerComment) {
            return InnerCommentListDto.builder()
                    .innerCommentId(innerComment.getId())
                    .user(innerComment.getUser())
                    .content(innerComment.getContent())
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
