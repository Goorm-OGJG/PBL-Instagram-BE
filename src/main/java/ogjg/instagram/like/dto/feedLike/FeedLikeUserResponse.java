package ogjg.instagram.like.dto.feedLike;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FeedLikeUserResponse {

    private Long feedId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private String userIntro;
    private LocalDateTime localDateTime;
    private boolean followStatus;

    public FeedLikeUserResponse putFollowStatus(boolean followStatus){
        this.followStatus = followStatus;
        return this;
    }

    public FeedLikeUserResponse(Long feedId, Long userId, String nickname, String profileImg, String userIntro, LocalDateTime localDateTime) {
        this.feedId = feedId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.userIntro = userIntro;
        this.localDateTime = localDateTime;
    }
}
