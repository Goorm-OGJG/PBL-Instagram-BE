package ogjg.instagram.hashtag.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.hashtag.domain.HashtagFeed;
import ogjg.instagram.hashtag.domain.HashtagPK;
import ogjg.instagram.hashtag.repository.HashtagFeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagFeedService {

    private final HashtagFeedRepository hashtagFeedRepository;

    @Transactional
    public List<HashtagFeed> saveAllHashtags(Feed feed, List<Hashtag> hashtags) {
        return hashtagFeedRepository.saveAll(
                hashtags.stream()
                        .map((hashtag) -> HashtagFeed.builder()
                                .hashtagPK(new HashtagPK(hashtag.getId(), feed.getId()))
                                .feed(feed)
                                .hashtag(hashtag)
                                .build())
                        .toList());
    }

    @Transactional(readOnly = true)
    public Long countTaggedFeeds(Long hashtagId) {
        return hashtagFeedRepository.countByHashtagId(hashtagId);
    }

}
