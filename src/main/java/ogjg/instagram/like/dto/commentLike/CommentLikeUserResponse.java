package ogjg.instagram.like.dto.commentLike;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentLikeUserResponse {

    private Long commentId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private String userIntro;
    private boolean followStatus;

    public CommentLikeUserResponse putFollowStatus(boolean followStatus){
        this.followStatus = followStatus;
        return this;
    }

    public CommentLikeUserResponse(Long commentId, Long userId, String nickname, String profileImg, String userIntro) {
        this.commentId = commentId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.userIntro = userIntro;
    }
}
