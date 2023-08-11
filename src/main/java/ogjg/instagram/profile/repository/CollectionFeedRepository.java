package ogjg.instagram.profile.repository;

import ogjg.instagram.user.domain.CollectionFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionFeedRepository extends JpaRepository<CollectionFeed, Long> {

    @Query("select cf from CollectionFeed cf join fetch cf.feed f  where cf.user.id = :userId")
    Page<CollectionFeed> findSavedFeeds(@Param("userId") Long userId, Pageable pageable);

}

