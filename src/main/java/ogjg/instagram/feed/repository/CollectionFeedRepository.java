package ogjg.instagram.feed.repository;

import ogjg.instagram.feed.domain.CollectionFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CollectionFeedRepository extends JpaRepository<CollectionFeed, Long> {

    @Query("select cf from CollectionFeed cf join fetch cf.feed f  where cf.user.id = :userId")
    Page<CollectionFeed> findCollectedFeeds(@Param("userId") Long userId, Pageable pageable);

    @Query("select cf from CollectionFeed cf" +
            " where cf.feed.id = :feedId" +
            " and cf.user.id = :userId")
    Optional<CollectionFeed> findByFeedId(@Param("feedId") Long feedId, @Param("userId") Long userId);

    @Modifying
    @Query("""
        delete from CollectionFeed cf
        where cf.feed.id = :feedId
        and cf.user.id = :userId
        and cf.collection.id = :collectionId
    """)
    void deleteBy(@Param("feedId") Long feedId, @Param("collectionId") Long collectionId, @Param("userId") Long userId);
}

