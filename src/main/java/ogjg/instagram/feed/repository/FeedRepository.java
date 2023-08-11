package ogjg.instagram.feed.repository;

import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.profile.dto.ProfileFeedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByUserId(Long jwt_userId);
    List<Feed> findAllByUserId(Long jwt_userId, Pageable pageable);
    Long countAllByUserId(Long userId);

    /**
     * 내 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @Query("""
       SELECT NEW ogjg.instagram.profile.dto.ProfileFeedResponseDto (
           f.id, 
           (SELECT fm.mediaUrl FROM FeedMedia fm WHERE fm.feed = f ORDER BY fm.id ASC),
           (SELECT COUNT(fm) > 1 FROM FeedMedia fm WHERE fm.feed = f),
           (SELECT COUNT(fl) FROM FeedLike fl WHERE fl.feed = f),
           (SELECT COUNT(c) FROM Comment c WHERE c.feed = f)
       )
       FROM Feed f
       WHERE f.user.id = :userId
    """)
    Page<ProfileFeedResponseDto> findMyFeedsByUserId(@Param("userId") Long jwt_userId, Pageable pageable);

    /**
     * "내가 보관한 피드 목록 가져오기 - 무한 스크롤 9개씩" 에 사용
     */
    @Query("select f from Feed f join fetch User u where f.id = :feedId")
    Optional<Feed> findUserByFeedId(Long feedId);
}
