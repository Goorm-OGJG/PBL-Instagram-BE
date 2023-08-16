package ogjg.instagram.feed.repository;

import ogjg.instagram.feed.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
