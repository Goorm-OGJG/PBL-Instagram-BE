package ogjg.instagram.profile.dto.response;

import lombok.Builder;
import lombok.Getter;
import ogjg.instagram.user.domain.User;

@Builder
@Getter
public class ProfileResponseDto {

    private String nickname;
    private String userImg;
    private String userIntro;
    private long followCount;
    private long followingCount;
    private long feedCount;
    private boolean followingStatus;
    private boolean secretStatus;

    public static ProfileResponseDto from(User user, Long feedCount, Long followCount, Long followingCount, boolean followingStatus) {
        return ProfileResponseDto.builder()
                .nickname(user.getNickname())
                .userImg(user.getUserImg())
                .userIntro(user.getUserIntro())
                .followCount(followCount)
                .followingCount(followingCount)
                .feedCount(feedCount)
                .followingStatus(followingStatus)
                .secretStatus(user.isSecret())
                .build();
    }
}