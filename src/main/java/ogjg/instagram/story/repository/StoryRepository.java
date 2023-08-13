package ogjg.instagram.story.repository;

import ogjg.instagram.story.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story,Long>, StoryRepositoryCustom {

}
