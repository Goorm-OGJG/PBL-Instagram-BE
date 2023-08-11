package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.repository.FeedRepository;
import ogjg.instagram.profile.dto.ProfileFeedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    @Transactional(readOnly = true)
    public Page<ProfileFeedResponseDto> findProfileFeedsByUserId(Long userId, Pageable pageable) {
        return feedRepository.findMyFeedsByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Long countAllByUserID(Long userId) {
        return feedRepository.countAllByUserId(userId);
    }

    public Feed findById(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 피드가 존재하지 않습니다. id=" + feedId));
    }

    @Transactional(readOnly = true)
    public Feed findDetailById(Long feedId) {
        return feedRepository.findUserByFeedId(feedId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 피드가 존재하지 않습니다. id=" + feedId));
    }
}
