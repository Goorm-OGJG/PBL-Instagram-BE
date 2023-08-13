package ogjg.instagram.like.repository;

import ogjg.instagram.like.domain.feedLike.FeedLike;
import ogjg.instagram.like.dto.feedLike.FeedLikeUserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike,Long> {

    @Modifying
    @Query("delete from FeedLike fl where fl.feedLikePK.feedId =:feedId and fl.feedLikePK.userId= :userId")
    void deleteFeedLike(@Param("feedId") Long feedId, @Param("userId") Long userId);

    @Query("""
        select new ogjg.instagram.like.dto.feedLike.FeedLikeUserResponse(f.id, u.id, u.nickname, u.userImg, u.userIntro, fl.createdAt)
        from Feed f 
        join FeedLike fl on f.id = fl.feed.id 
        join User u on u.id = fl.user.id 
        where f.id = :feedId
        order by fl.createdAt asc
    """)
    List<FeedLikeUserResponse> feedLikeUserList(@Param("feedId") Long feedId, Pageable pageable);

    @Query("select count(*) from FeedLike fl where fl.feedLikePK.feedId = :feedId")
    Long countLikes(@Param("feedId") Long feedId);

    @Query("""
            select fl from FeedLike fl where fl.feed.id = :feedId and fl.user.id = :userId  
    """)
    Optional<FeedLike> checkLikeStatus(@Param("feedId") Long feedId, @Param("userId") Long userId);
}
