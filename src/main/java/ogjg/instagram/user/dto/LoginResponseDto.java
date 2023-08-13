package ogjg.instagram.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private Long id;
    private String nickname;
    private String userImg;

    public LoginResponseDto(Long id, String nickname, String userImg) {
        this.id = id;
        this.nickname = nickname;
        this.userImg = userImg;
    }
}
