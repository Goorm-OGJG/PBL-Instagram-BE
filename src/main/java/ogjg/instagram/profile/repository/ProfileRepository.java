package ogjg.instagram.profile.repository;

import ogjg.instagram.profile.dto.response.ProfileFeedResponseDto;
import ogjg.instagram.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<User,Long> {

    /**
     * 내 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @Query("""
       SELECT NEW ogjg.instagram.profile.dto.response.ProfileFeedResponseDto$ProfileFeedDto (
           f.id, 
           (SELECT fm.mediaUrl FROM FeedMedia fm WHERE fm.feed = f ORDER BY fm.id ASC),
           (SELECT COUNT(fm) > 1 FROM FeedMedia fm WHERE fm.feed = f),
           (SELECT COUNT(fl) FROM FeedLike fl WHERE fl.feed = f),
           (SELECT COUNT(c) FROM Comment c WHERE c.feed = f)
       )
       FROM Feed f
       WHERE f.user.id = :userId
    """)
    Page<ProfileFeedResponseDto.ProfileFeedDto> findMyFeedsByUserId(@Param("userId") Long jwt_userId, Pageable pageable);
}
