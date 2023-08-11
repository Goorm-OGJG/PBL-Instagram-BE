package ogjg.instagram.like.repository;

import ogjg.instagram.like.domain.storyLike.StoryLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryLikeRepository extends JpaRepository<StoryLike,Long> {


    @Modifying
    @Query("delete from StoryLike sl where sl.storyLikePK.storyId =:storyId and sl.storyLikePK.userId= :userId")
    void deleteStoryLike(@Param("storyId") Long storyId, @Param("userId") Long userId);

    @Query("select count(*) from StoryLike sl where sl.storyLikePK.storyId=:storyId")
    Long countLikes(@Param("storyId") Long storyId);

}
