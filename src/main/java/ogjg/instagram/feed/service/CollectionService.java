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

    /**
     * 1) 컬렉션-피드 테이블에 값을 추가한다.
     * 2) 컬렉션 테이블에 default 네임인 1을 가지는 데이터를 추가한다.
     * --> 후에 컬렉션-피드 테이블을 통해 컬렉션 추가 여부를 확인한다.
     */
    @Transactional
    public void collectFeed(Long feedId, String collectionName, Long userId) {
        Feed findFeed = feedService.findById(feedId);
        User findUser = userService.findById(userId);
        Collection collection = findByUserId(userId, findUser, collectionName);

        collectionFeedRepository.save(CollectionFeed.of(findFeed, collection, findUser));
    }

    /**
     * Collection을 생성한 적 있다면 Collection을 가져온다.
     * 생성한 적 없다면 default name으로 생성해서 반환한다.
     */
    private Collection findByUserId(Long userId, User findUser, String collectionName) {
        return collectionRepository.findByUserId(userId)
                .orElse(Collection.of(findUser, collectionName));
    }

    @Transactional
    public void deleteFeed(Long feedId, String collectionName, Long userId) {
        collectionFeedRepository.findById(feedId, collectionName, userId)
                .orElseThrow(() -> new IllegalArgumentException("저장되지 않은 피드입니다. feed id=" + feedId));

        collectionFeedRepository.deleteBy(feedId, collectionName, userId);
    }
}
