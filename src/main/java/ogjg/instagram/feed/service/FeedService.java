package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.dto.request.FeedCreateRequestDto;
import ogjg.instagram.feed.dto.response.FeedDetailResponseDto;
import ogjg.instagram.feed.dto.response.FeedListResponseDto;
import ogjg.instagram.feed.dto.response.FeedMediaResponseDto;
import ogjg.instagram.feed.repository.FeedRepository;
import ogjg.instagram.follow.service.FollowService;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.hashtag.domain.HashtagFeed;
import ogjg.instagram.hashtag.service.HashtagFeedService;
import ogjg.instagram.hashtag.service.HashtagService;
import ogjg.instagram.like.service.CommentLikeService;
import ogjg.instagram.like.service.FeedLikeService;
import ogjg.instagram.like.service.InnerCommentLikeService;
import ogjg.instagram.profile.dto.response.ProfileFeedResponseDto;
import ogjg.instagram.profile.repository.ProfileRepository;
import ogjg.instagram.profile.service.ProfileService;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedLikeService feedLikeService;
    private final FeedRepository feedRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final FollowService followService;
    private final HashtagFeedService hashtagFeedService;
    private final HashtagService hashtagService;
    private final UserService userService;
    private final CommentLikeService commentLikeService;
    private final InnerCommentLikeService innerCommentLikeService;


    @Transactional(readOnly = true)
    public ProfileFeedResponseDto findProfileFeedsByUserId(Long userId, Pageable pageable) {
        userService.findById(userId);
        return ProfileFeedResponseDto.from(profileRepository.findMyFeedsByUserId(userId, pageable));
    }

    @Transactional(readOnly = true)
    public Feed findById(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 피드가 존재하지 않습니다. id=" + feedId));
    }

    @Transactional(readOnly = true)
    public Feed findDetailById(Long feedId) {
        return feedRepository.findUserByFeedId(feedId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 피드가 존재하지 않습니다. id=" + feedId));
    }

    @Transactional
    public List<HashtagFeed> write(Long userId, FeedCreateRequestDto requestDto) {
        List<Hashtag> hashtags = hashtagService.saveAll(requestDto.toHashtags());

        User user = userService.findById(userId);

        log.info("requestDto={}",requestDto);

        Feed collectedFeed = feedRepository.save(requestDto.toFeed(user));
        log.info("feedId ={}", collectedFeed.getId());
        log.info("feedContent ={}", collectedFeed.getContent());

        return hashtagFeedService.saveAllHashtags(collectedFeed, hashtags);
    }

    @Transactional
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }

    @Transactional(readOnly = true)
    public Page<Feed> findFeedsIn(List<Long> followedIds, Pageable pageable) {
        return feedRepository.findFeedsIn(followedIds, pageable);
    }

    @Transactional
    public void delete(Long userId, Long feedId) {
        if (notMyFeed(userId, feedId)) throw new IllegalArgumentException("본인의 게시글만 삭제할 수 있습니다.");

        // todo : cascade는 쿼리문이 여러개 나간다.
        Feed feed = findById(feedId);
        feed.clearAll();
//        feedRepository.deleteById(feedId);
    }

    private boolean notMyFeed(Long userId, Long feedId) {
        User findUser = findById(feedId).getUser();
        return findUser.getId() != userId;
    }

    @Transactional(readOnly = true)
    public FeedListResponseDto findFeedList(Long userId, Pageable pageable) {
        List<Long> followedIds = followService.getFollowedIds(userId);
        Page<Feed> feedPages = findFeedsIn(followedIds, pageable);

        return FeedListResponseDto.from(
                feedPages.getContent().stream()
                        .map((feed) -> toFeedListDto(feed, feed.getUser().getId()))
                        .collect(toUnmodifiableList()),
                feedPages.isLast()
        );
    }

    private FeedListResponseDto.FeedListDto toFeedListDto(Feed feed, Long userId) {
        Long feedId = feed.getId();

        return FeedListResponseDto.FeedListDto.builder()
                .user(feed.getUser())
                .feed(feed)
                .likeCount(feedLikeService.feedLikeCount(feedId))
                .likeStatus(feedLikeService.isFeedLiked(feedId, userId))
                .collectionStatus(profileService.isCollected(feedId, userId))
                .feedMedias(feed.getFeedMedias().stream().map((FeedMediaResponseDto::new)).toList())
                .build();
    }

    //todo : Map 조회 단순화
    @Transactional(readOnly = true)
    public FeedDetailResponseDto findDetail(Long feedId, Long userId) {
        Feed feed = findDetailById(feedId);

        return FeedDetailResponseDto.from(
                findDetailById(feedId),
                feedLikeService.feedLikeCount(feedId),
                feedLikeService.isFeedLiked(feedId, userId),
                profileService.isCollected(feedId, userId),
                feed.getComments().stream()
                        .map(comment -> toCommentThumbnailResponseDto(comment, userId))
                        .collect(toUnmodifiableList())
        );
    }


    //todo : 로직을 한번에 처리하는 방법?
    private FeedDetailResponseDto.CommentThumbnailDto toCommentThumbnailResponseDto(Comment comment, Long userId) {
        Long commentId = comment.getId();

        return new FeedDetailResponseDto.CommentThumbnailDto(
                comment,
                userService.findById(userId),
                commentLikeService.commentLikeCount(commentId),
                commentLikeService.isCommentLiked(commentId, userId),
                innerCommentLikeService.countInnerComment(commentId));
    }
}
