package ogjg.instagram.follow.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Setter @Getter
public class FollowPK implements Serializable {
    private Long userId;
    private Long followId;
}