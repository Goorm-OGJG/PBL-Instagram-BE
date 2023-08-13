package ogjg.instagram.hashtag.repository;

import ogjg.instagram.hashtag.domain.HashtagFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagFeedRepository extends JpaRepository<HashtagFeed, Long> {
    @Query("""
            SELECT hf FROM HashtagFeed hf
            join fetch hf.feed f
            join fetch hf.hashtag h WHERE h.content LIKE :searchKey
            """)
    Page<HashtagFeed> findByHashtagContaining(@Param("searchKey") String searchKey, Pageable pageable);

    Long countByHashtagId(Long hashtagId);
}
