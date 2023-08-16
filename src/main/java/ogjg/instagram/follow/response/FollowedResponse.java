package ogjg.instagram.follow.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowedResponse {

    private Long userId;
    private Long followId;
    private String nickname;
    private String profileImg;
    private boolean followingStatus;

    @Builder
    public FollowedResponse(FollowResponse followResponse, boolean followingStatus) {
        this.userId = followResponse.getUserId();
        this.followId = followResponse.getFollowId();
        this.nickname = followResponse.getNickname();
        this.profileImg = followResponse.getProfileImg();
        this.followingStatus = followingStatus;
    }
}
