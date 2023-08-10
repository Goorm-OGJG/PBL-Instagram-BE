package ogjg.instagram.profile.dto.response;

import lombok.Builder;
import lombok.Getter;
import ogjg.instagram.user.domain.User;

@Builder
@Getter
public class ProfileEditResponseDto {
    private Long id;
    private String nickname;
    private String profileImg;
    private String userIntro;
    private boolean isRecommended;
    private boolean isSecret;


    public static ProfileEditResponseDto from(User user) {
        return ProfileEditResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getUserImg())
                .userIntro(user.getUserIntro())
                .isRecommended(user.isRecommend())
                .isSecret(user.isSecret())
                .build();
    }
}
