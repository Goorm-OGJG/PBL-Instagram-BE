package ogjg.instagram.feed.repository;

import ogjg.instagram.feed.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Optional<Collection> findByUserId(Long userId);
}
