package ogjg.instagram.story.repository;


import ogjg.instagram.story.dto.response.StoryListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoryRepositoryCustom {

    List<StoryListDto> storyList();

    Long storyListCount();


}
