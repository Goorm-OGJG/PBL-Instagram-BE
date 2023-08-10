package ogjg.instagram.search.dto;

import lombok.Builder;
import ogjg.instagram.user.domain.User;

public class SearchResponse {

    String profileImg;
    String nickname;
    String userIntro;
    boolean followingStatus;

    @Builder
    public SearchResponse(String profileImg, String nickname, String userIntro, boolean followingStatus) {
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.userIntro = userIntro;
        this.followingStatus = followingStatus;
    }

    public static SearchResponse of(User user, boolean followingStatus) {
        return SearchResponse.builder()
                .profileImg(user.getUserImg())
                .nickname(user.getNickname())
                .userIntro(user.getUserIntro())
                .followingStatus(followingStatus)
                .build();
    }
}
