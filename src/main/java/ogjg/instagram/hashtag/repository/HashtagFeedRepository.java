package ogjg.instagram.hashtag.repository;

import ogjg.instagram.hashtag.domain.HashtagFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagFeedRepository extends JpaRepository<HashtagFeed, Long> {
    Long countByHashtagId(Long hashtagId);
}
