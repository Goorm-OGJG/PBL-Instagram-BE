package ogjg.instagram.story.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StoryResponse {
    List<StoryListDto> storyList = new ArrayList<>();

    public StoryResponse(List<StoryListDto> storyList) {
        this.storyList = storyList;
    }
}
