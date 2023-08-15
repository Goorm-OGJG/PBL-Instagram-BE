package ogjg.instagram.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class UserAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    private String nickname;

    @Column(unique = true)
    private String refreshToken;

    @Builder
    public UserAuthentication(Long id, Long userId, String username, String nickname, String refreshToken) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
