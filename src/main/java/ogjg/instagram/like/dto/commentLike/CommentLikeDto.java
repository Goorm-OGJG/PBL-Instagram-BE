package ogjg.instagram.like.dto.commentLike;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentLikeDto {

    private Long commentId;
    private Long userId;
    private LocalDateTime createdAt;

    public CommentLikeDto(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
