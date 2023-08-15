package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.Collection;
import ogjg.instagram.feed.domain.CollectionFeed;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.repository.CollectionFeedRepository;
import ogjg.instagram.feed.repository.CollectionRepository;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionFeedRepository collectionFeedRepository;
    private final CollectionRepository collectionRepository;
    private final FeedService feedService;
    private final UserService userService;

    @Transactional
    public void collectFeed(Long feedId, Long collectionId, Long userId) {
        Feed findFeed = feedService.findById(feedId);
        User findUser = userService.findById(userId);
        Collection findCollection = findById(collectionId);

        collectionFeedRepository.save(CollectionFeed.of(findFeed, findCollection, findUser));
    }

    @Transactional
    public void deleteFeed(Long feedId, Long collectionID,Long userId) {
        collectionFeedRepository.deleteBy(feedId, collectionID,userId);
    }

    @Transactional(readOnly = true)
    public Collection findById(Long id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬렉션입니다. id ="+id));
    }
}
