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
    public static final int ZERO = 0;
    private final CollectionFeedRepository collectionFeedRepository;
    private final CollectionFeedService collectionFeedService;
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
        if (collectionFeedService.isCollected(feedId, userId)) {
            throw new IllegalArgumentException("이미 수집한 피드입니다.");
        }

        Feed findFeed = feedService.findById(feedId);
        User findUser = userService.findById(userId);
        Collection collection = findByUserKey(userId, findUser, collectionName);

        collectionRepository.save(collection); //todo: 저장 로직 개선
        collectionFeedService.save(CollectionFeed.of(findFeed, collection, findUser));
    }

    /**
     * Collection을 생성한 적 있다면 Collection을 가져온다.
     * 생성한 적 없다면 default name으로 생성해서 반환한다.
     */
    @Transactional(readOnly = true)
    public Collection findByUserKey(Long userId, User findUser, String collectionName) {
        return collectionRepository.findByUserId(userId)
                .orElse(Collection.of(findUser, collectionName));
    }

    /**
     * todo: 우선 유저이름만 사용해서 삭제한다.
     * 원래는 조회부터 userId와 name을 모두 사용해야하고, 삭제일때도 지정해서 삭제해야한다. -> 컬렉션이 1개만 존재하므로 userId만으로 조회
     * 삭제 로직도 매번 collection을 삭제할때마다 쿼리로 검사를 해야하는지도 고민해야한다. -> 우선 삭제마다 존재 여부를 count한다.
     */
    @Transactional
    public void deleteFeed(Long feedId, String collectionName, Long userId) {
        CollectionFeed collectionFeed = collectionFeedService.findByKey(feedId, collectionName, userId);

        collectionFeedRepository.delete(collectionFeed);

        // 조회해서 보관한 피드가 마지막이면 collection row도 삭제한다.
        if (noMoreCollection(userId)) {
            Collection findCollection = findByUserKey(userId);
            collectionRepository.delete(findCollection);
        }
    }

    private boolean noMoreCollection(Long userId) {
       return collectionFeedService.countCollectionFeed(userId) == ZERO;
    }

    @Transactional(readOnly = true)
    public Collection findByUserKey(Long userId) {
        return collectionRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 존재하지 않습니다. user id=" + userId));
    }
}
