package ogjg.instagram.follow.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.follow.response.FollowResponse;

import java.io.Serializable;


@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class FollowPK implements Serializable {

    private Long userId;
    private Long followId;

    public FollowPK(FollowResponse followResponse) {
        this.userId = followResponse.getUserId();
        this.followId = followResponse.getFollowId();
    }

}