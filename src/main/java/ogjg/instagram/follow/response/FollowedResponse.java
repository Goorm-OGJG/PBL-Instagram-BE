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
    private boolean followingStatus;

    @Builder
    public FollowedResponse(FollowResponse followResponse, boolean followingStatus) {
        this.userId = followResponse.getUserId();
        this.followId = followResponse.getFollowId();
        this.followingStatus = followingStatus;
    }
}
