package ogjg.instagram.story.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StoryListDto {

    private Long storyId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private LocalDateTime createdAt;
    private List<StoryMediaDto> mediaList = new ArrayList<>();
    private boolean readAll;

    public StoryListDto putMediaList(List<StoryMediaDto> mediaList) {
        this.mediaList = mediaList;
        return this;
    }

    public StoryListDto putReadAll(boolean readAll) {
        this.readAll = readAll;
        return this;
    }

    @Builder
    @QueryProjection
    public StoryListDto(Long storyId, Long userId, String nickname, String profileImg, LocalDateTime createdAt) {
        this.storyId = storyId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.createdAt = createdAt;
    }
}
