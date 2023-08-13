package ogjg.instagram.feed.repository;

import ogjg.instagram.feed.domain.Feed;
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

    @Query("""
            select f from Feed f 
            join fetch f.user u where f.id = :feedId""")
    Optional<Feed> findUserByFeedId(@Param("feedId") Long feedId);

    @Query("""
            select f from Feed f 
                join fetch f.user u
                where u.id in :ids
            """)
    Page<Feed> findFeedsIn(@Param("ids") List<Long> ids, Pageable pageable);


}
