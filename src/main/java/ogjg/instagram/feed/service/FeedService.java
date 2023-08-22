package ogjg.instagram.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.comment.service.CommentService;
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

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedLikeService feedLikeService;
    private final FeedRepository feedRepository;
    private final FeedMediaService feedMediaService;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final FollowService followService;
    private final HashtagFeedService hashtagFeedService;
    private final HashtagService hashtagService;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final InnerCommentLikeService innerCommentLikeService;


    @Transactional(readOnly = true)
    public ProfileFeedResponseDto findProfileFeedsByUserId(String nickname, Pageable pageable) {
        User user = profileRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. nickname=" + nickname));
        return ProfileFeedResponseDto.from(profileRepository.findMyFeedsByUserId(user.getId(), pageable));
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

        feedMediaService.saveAll(collectedFeed);

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
        feedRepository.delete(feed);
    }

    private boolean notMyFeed(Long userId, Long feedId) {
        User findUser = findById(feedId).getUser();
        return findUser.getId() != userId;
    }

    @Transactional(readOnly = true)
    public FeedListResponseDto findFeedList(Long userId, Pageable pageable) {
        // 자신의 게시물도 보이도록 추가하기 위해 가변 리스트로 변형
        List<Long> followedIds = new ArrayList<>(followService.getFollowedIds(userId));
        followedIds.add(userId);

        Page<Feed> feedPages = findFeedsIn(followedIds, pageable);

        return FeedListResponseDto.from(
                feedPages.getContent().stream()
                        .map((feed) -> toFeedListDto(feed, userId))
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
                .commentCount(commentService.countTotalComment(feedId))
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
        Long commentUserId = comment.getUser().getId();

        return new FeedDetailResponseDto.CommentThumbnailDto(
                comment,
                userService.findById(commentUserId),
                commentLikeService.commentLikeCount(commentId),
                commentLikeService.isCommentLiked(commentId, userId),
                innerCommentLikeService.countInnerComment(commentId));
    }
}
