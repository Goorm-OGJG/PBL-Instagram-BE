package ogjg.instagram.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class JwtUserClaimsDto {
    private Long userId;
    private String username;
    private String nickname;

    public JwtUserClaimsDto(Long userId, String username, String nickname) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }
}
