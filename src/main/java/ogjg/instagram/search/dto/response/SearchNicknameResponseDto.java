package ogjg.instagram.search.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.user.domain.User;

import java.util.List;

@NoArgsConstructor
@Getter
public class SearchNicknameResponseDto {
    private boolean isUser;
    private List<SearchNicknameDto> userList;


    @Builder
    public SearchNicknameResponseDto(List<SearchNicknameDto> userList, boolean isUser) {
        this.userList = userList;
        this.isUser = isUser;
    }

    public static SearchNicknameResponseDto from(List<SearchNicknameDto> searchResult, boolean isUser) {
        return SearchNicknameResponseDto.builder()
                .isUser(isUser)
                .userList(searchResult)
                .build();
    }

    @Getter
    public static class SearchNicknameDto {
        private String profileImg;
        private String nickname;
        private String userIntro;
        private boolean followingStatus;

        @Builder
        public SearchNicknameDto(String profileImg, String nickname, String userIntro, boolean followingStatus) {
            this.profileImg = profileImg;
            this.nickname = nickname;
            this.userIntro = userIntro;
            this.followingStatus = followingStatus;
        }

        public static SearchNicknameDto of(User user, boolean followingStatus) {
            return SearchNicknameDto.builder()
                    .profileImg(user.getUserImg())
                    .nickname(user.getNickname())
                    .userIntro(user.getUserIntro())
                    .followingStatus(followingStatus)
                    .build();
        }
    }
}
