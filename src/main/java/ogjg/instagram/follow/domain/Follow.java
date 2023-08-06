package ogjg.instagram.follow.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Follow {

    @EmbeddedId
    private FollowPK followPK;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    private User user;

    @MapsId("followId")
    @ManyToOne(fetch = LAZY)
    private User followUser;
}
