package ogjg.instagram.hashtag.repository;

import ogjg.instagram.hashtag.domain.HashtagFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagFeedRepository extends JpaRepository<HashtagFeed, Long> {
    @Query("""
            SELECT count(hf.feed.id) FROM HashtagFeed hf
            join hf.hashtag h WHERE h.content =:content
            """)
    Long countTaggedFeeds(@Param("content") String content);
}
