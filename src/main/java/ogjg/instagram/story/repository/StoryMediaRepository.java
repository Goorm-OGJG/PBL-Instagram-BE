package ogjg.instagram.story.repository;

import ogjg.instagram.story.domain.StoryMedia;
import ogjg.instagram.story.dto.response.StoryMediaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryMediaRepository extends JpaRepository<StoryMedia,Long> {

    @Query("select new ogjg.instagram.story.dto.response.StoryMediaDto(sm.id,sm.story.id,sm.mediaType,sm.mediaUrl,sm.createdAt) from StoryMedia sm where sm.story.id = :storyId")
    List<StoryMediaDto> findAllStoryMedia(@Param("storyId") Long storyId);


    @Modifying
    @Query("delete from StoryMedia sm where sm.story.id = :storyId")
    void mediaDeleteAll(@Param("storyId") Long storyId);



}
