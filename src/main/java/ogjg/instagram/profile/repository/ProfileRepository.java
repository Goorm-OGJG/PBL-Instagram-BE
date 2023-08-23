package ogjg.instagram.profile.repository;

import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User,Long> {

    /**
     * 내 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @Query("""
       SELECT f FROM Feed f WHERE f.user.id = :userId
    """)
    Page<Feed> findMyFeedsByUserId(@Param("userId") Long userId, Pageable pageable);


    Optional<User> findByNickname(String nickname);
}
