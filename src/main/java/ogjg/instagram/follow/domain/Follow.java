package ogjg.instagram.follow.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.follow.response.FollowResponse;
import ogjg.instagram.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Follow {

    @EmbeddedId
    private FollowPK followPK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("followId")
    @ManyToOne(fetch = LAZY)
    private User follow;

    public Follow(FollowResponse followResponse, User user, User follow) {
        this.followPK = new FollowPK(followResponse);
        this.user = user;
        this.follow = follow;
    }

}
