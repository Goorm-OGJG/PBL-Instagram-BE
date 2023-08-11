package ogjg.instagram.profile.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.profile.repository.CollectionFeedRepository;
import ogjg.instagram.user.domain.CollectionFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final CollectionFeedRepository collectionFeedRepository;

    public Page<CollectionFeed> findSavedFeeds(Long userId, Pageable pageable) {
        return collectionFeedRepository.findSavedFeeds(userId, pageable);
    }
}
