package ogjg.instagram.hashtag.repository;

import ogjg.instagram.hashtag.domain.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    @Query("""
            SELECT h.content FROM Hashtag h
            join h.hashtagFeeds hf WHERE h.content LIKE :searchKey
            group by h.content
            """)
    Page<String> findByContentsContaining(@Param("searchKey") String searchKey, Pageable pageable);
}
