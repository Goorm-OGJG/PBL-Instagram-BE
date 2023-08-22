package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.domain.FeedMedia;
import ogjg.instagram.feed.repository.FeedMediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedMediaService {

    private final FeedMediaRepository feedMediaRepository;

    @Transactional
    public void saveAll(Feed feed) {
        List<FeedMedia> feedMedias = feed.getFeedMedias();
        for (FeedMedia feedMedia : feedMedias) {
            log.info("FeedMedia={}", feedMedia);
        }

//        feedMedias.stream()
//                .forEach((feedMedia -> feedMedia.setFeed(feed)));

        feedMediaRepository.saveAll(feedMedias);
    }
}
