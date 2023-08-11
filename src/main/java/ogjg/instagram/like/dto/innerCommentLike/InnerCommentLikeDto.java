package ogjg.instagram.like.dto.innerCommentLike;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class InnerCommentLikeDto {

    private Long innerCommentId;
    private Long userId;
    private LocalDateTime createdAt;

    public InnerCommentLikeDto(Long innerCommentId, Long userId) {
        this.innerCommentId = innerCommentId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
