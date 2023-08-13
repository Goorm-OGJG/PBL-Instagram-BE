package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.repository.CollectionFeedRepository;
import ogjg.instagram.feed.domain.CollectionFeed;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionFeedRepository collectionFeedRepository;
    private final FeedService feedService;
    private final UserService userService;

    @Transactional
    public void collectFeed(Long feedId, Long userId) {
        Feed findFeed = feedService.findById(feedId);
        User findUser = userService.findById(userId);

        collectionFeedRepository.save(CollectionFeed.of(findFeed, findUser));
    }

    @Transactional
    public void deleteFeed(Long feedId, Long collectionID,Long userId) {
        collectionFeedRepository.deleteBy(feedId, collectionID,userId);
    }
}
