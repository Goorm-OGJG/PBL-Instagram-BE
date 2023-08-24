package ogjg.instagram.search.repository;

import ogjg.instagram.feed.domain.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Feed,Long> {

    @Query("""
    select f from Feed f
        join HashtagFeed hf on f.id = hf.feed.id 
        join Hashtag h on hf.hashtag.id = h.id
        where h.content = :content
""")
    List<Feed> findFeedByContent(@Param("content") String content, Pageable pageable);

    @Query("""
    select count(f) from Feed f
        join HashtagFeed hf on f.id = hf.feed.id 
        join Hashtag h on hf.hashtag.id = h.id
        where h.content = :content
""")
    Long countFeedByContent(@Param("content") String content);
}
