package ogjg.instagram.profile.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.repository.FeedRepository;
import ogjg.instagram.follow.service.FollowService;
import ogjg.instagram.profile.dto.response.ProfileFeedResponseDto;
import ogjg.instagram.profile.dto.response.ProfileResponseDto;
import ogjg.instagram.feed.repository.CollectionFeedRepository;
import ogjg.instagram.profile.repository.ProfileRepository;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final CollectionFeedRepository collectionFeedRepository;
    private final UserService userService;
    private final FeedRepository feedRepository;
    private final FollowService followService;
    private final ProfileRepository profileRepository;

    @Transactional
    public ProfileFeedResponseDto findCollectedFeeds(Long userId, Pageable pageable) {
        return ProfileFeedResponseDto.fromCollected(collectionFeedRepository.findCollectedFeeds(userId, pageable));
    }
    @Transactional
    public boolean isCollected(Long feedId, Long userId) {
        return collectionFeedRepository.findByFeedId(feedId, userId).isPresent();
    }

    ///todo : Map 조회 단순화
    @Transactional(readOnly = true)
    public ProfileResponseDto findProfile(String nickname, Long loginId) {
        User user = profileRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. id=" + loginId));
        Long userId = user.getId();

        return ProfileResponseDto.from(
                userService.findById(userId),
                feedRepository.countAllByUserId(userId),
                followService.followedCount(userId),
                followService.followingCount(loginId),
                followService.isFollowing(userId, loginId)
        );
    }
}
