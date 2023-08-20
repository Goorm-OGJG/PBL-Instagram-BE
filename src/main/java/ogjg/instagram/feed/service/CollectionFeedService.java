package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.CollectionFeed;
import ogjg.instagram.feed.repository.CollectionFeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionFeedService {

    private final CollectionFeedRepository collectionFeedRepository;

    @Transactional(readOnly = true)
    public CollectionFeed findByKey(Long feedId, String collectionName, Long userId) {
        return collectionFeedRepository.findByKey(feedId, collectionName, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 피드입니다. feed id=" + feedId));
    }

    public CollectionFeed save(CollectionFeed collection) {
        return collectionFeedRepository.save(collection);
    }

    @Transactional(readOnly = true)
    public boolean isCollected(Long feedId, Long userId) {
        return collectionFeedRepository.findByKeys(feedId, userId).isPresent();
    }

    @Transactional(readOnly = true)
    public Long countCollectionFeed(Long userId) {
        return collectionFeedRepository.countByUserId(userId);
    }
}
