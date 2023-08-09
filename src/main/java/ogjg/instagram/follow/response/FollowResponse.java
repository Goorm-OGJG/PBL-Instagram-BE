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

    @Builder
    public FollowResponse(Long userId, Long followId) {
        this.userId = userId;
        this.followId = followId;
    }
}
