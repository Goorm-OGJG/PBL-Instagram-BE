package ogjg.instagram.like.dto.innerCommentLike;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InnerCommentLikeUserResponse {

    private Long innerCommentId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private String userIntro;
    private boolean followStatus;

    public InnerCommentLikeUserResponse putFollowStatus(boolean followStatus){
        this.followStatus = followStatus;
        return this;
    }

    public InnerCommentLikeUserResponse(Long commentId, Long userId, String nickname, String profileImg, String userIntro) {
        this.innerCommentId = commentId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.userIntro = userIntro;
    }
}
