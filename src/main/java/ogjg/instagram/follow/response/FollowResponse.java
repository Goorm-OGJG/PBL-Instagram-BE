package ogjg.instagram.follow.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowResponse {

    private Long userId;
    private Long followId;
    private String nickname;
    private String profileImg;

    @Builder
    public FollowResponse(Long userId, Long followId, String nickname, String profileImg) {
        this.userId = userId;
        this.followId = followId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    @Builder
    public FollowResponse(Long userId, Long followId) {
        this.userId = userId;
        this.followId = followId;
    }
}
