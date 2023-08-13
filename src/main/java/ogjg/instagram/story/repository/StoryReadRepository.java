package ogjg.instagram.story.repository;

import ogjg.instagram.story.dto.request.StoryUserReadDto;
import ogjg.instagram.user.domain.StoryUserRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoryReadRepository extends JpaRepository<StoryUserRead,Long> {

    @Query("select new ogjg.instagram.story.dto.request.StoryUserReadDto(su.storyUserReadPK.userId , su.storyUserReadPK.storyId) from StoryUserRead su where su.storyUserReadPK.storyId =:storyId and su.storyUserReadPK.userId =:userId")
    Optional<StoryUserReadDto> showStoryRead(@Param("userId") Long userId, @Param("storyId") Long storyId);

}
