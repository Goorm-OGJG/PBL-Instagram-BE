package ogjg.instagram.hashtag.repository;

import ogjg.instagram.hashtag.domain.HashtagFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagFeedRepository extends JpaRepository<HashtagFeed, Long> {
    @Query("""
        SELECT h.content, count(hf.feed.id)
        FROM Hashtag h
        join h.hashtagFeeds hf 
        WHERE h.content LIKE :searchKey
        GROUP BY h.content
        """)
    Page<Object[]> countTaggedFeedsByHashtag(@Param("searchKey") String searchKey, Pageable pageable);
}
