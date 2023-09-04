package ogjg.instagram.hashtag.repository;

import ogjg.instagram.hashtag.domain.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByContent(String hashtag);

    @Query("""
            SELECT h FROM Hashtag h
            WHERE h.content LIKE :searchKey
            """)
    Page<Hashtag> findByHashtagContaining(@Param("searchKey") String searchKey, Pageable pageable);
}
