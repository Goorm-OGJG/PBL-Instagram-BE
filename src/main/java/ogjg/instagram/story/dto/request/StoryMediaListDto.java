package ogjg.instagram.story.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class StoryMediaListDto {
    private List<String> mediaList = new ArrayList<>();
}
