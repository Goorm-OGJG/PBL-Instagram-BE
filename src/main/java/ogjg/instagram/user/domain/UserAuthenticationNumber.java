package ogjg.instagram.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthenticationNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String authenticationCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Builder
    public UserAuthenticationNumber(Long userId, String authenticationCode, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.userId = userId;
        this.authenticationCode = authenticationCode;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
