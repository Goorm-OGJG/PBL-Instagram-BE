package ogjg.instagram.search.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.repository.CommentRepository;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.repository.FeedMediaRepository;
import ogjg.instagram.follow.service.FollowService;
import ogjg.instagram.hashtag.domain.HashtagFeed;
import ogjg.instagram.hashtag.service.HashtagFeedService;
import ogjg.instagram.like.repository.FeedLikeRepository;
import ogjg.instagram.search.dto.response.SearchHashtagResponseDto;
import ogjg.instagram.search.dto.response.SearchHashtagResultResponseDto;
import ogjg.instagram.search.dto.response.SearchNicknameResponseDto;
import ogjg.instagram.search.repository.SearchRepository;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final HashtagFeedService hashtagFeedService;
    private final FollowService followService;
    private final SearchRepository searchRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final CommentRepository commentRepository;
    private final FeedMediaRepository feedMediaRepository;

    //todo : slice 알아보기
    @Transactional(readOnly = true)
    public SearchHashtagResponseDto searchByHashtag(boolean isUser, String searchKey, Pageable pageable) {
        return SearchHashtagResponseDto.from(
                hashtagFeedService.findByHashtagContaining(wildCard(searchKey), pageable)
                        .getContent().stream()
                        .map(this::toSearchHashtagDto)
                        .collect(Collectors.toUnmodifiableList()),
                isUser
        );

    }

    private SearchHashtagResponseDto.SearchHashtagDto toSearchHashtagDto(HashtagFeed hashtagFeed) {
        return  SearchHashtagResponseDto.SearchHashtagDto.of(
                hashtagFeed,
                hashtagFeedService.countTaggedFeeds(hashtagFeed.getHashtag().getId())
        );
    }

    @Transactional(readOnly = true)
    public SearchNicknameResponseDto searchByNickname(boolean isUser, Long loginId, String searchKey, Pageable pageable) {
        return SearchNicknameResponseDto.from(
                userRepository.findByNicknameContaining(wildCard(searchKey), pageable)
                        .getContent().stream()
                        .map((user) -> SearchNicknameResponseDto.SearchNicknameDto.of(
                                user,
                                followService.isFollowingUser(loginId, user.getId())))
                        .collect(Collectors.toUnmodifiableList()),
                isUser);
    }

    private String wildCard(String search) {
        return "%" + search.trim() + "%";
    }

    /**
     * 애초에 해당 해시태그가 한번도 사용되지 않았다면, 검색에 등장하지 않는다.
     * 만약의 경우 태그가 1개 남아있다가 사라질때의 동시성 처리에 대비해서 count값을 확인하도록 했다.
     */
    @Transactional(readOnly = true)
    public SearchHashtagResultResponseDto searchResult(String content, Pageable pageable) {
        Long feedCount = searchRepository.countFeedByContent(content);
        if (feedCount == 0) {
            throw new IllegalArgumentException("해당 해시태그를 사용한 게시물이 존재하지 않습니다. hashtag=" + content);
        }

        List<Feed> feedByContent = searchRepository.findFeedByContent(content, pageable);
        String thumbnail = feedByContent.get(0).getFeedMedias().get(0).getMediaUrl();

        return SearchHashtagResultResponseDto.from(
                feedByContent.stream()
                        .map(this::toSearchHashtagResultDto)
                        .toList(),
                feedCount,
                content,
                thumbnail
        );
    }

    // todo : 여기서 count들을 group으로 한 번에 받아오기
    private SearchHashtagResultResponseDto.SearchHashtagResultDto toSearchHashtagResultDto(Feed feed) {
        return SearchHashtagResultResponseDto.SearchHashtagResultDto.builder()
                .feedId(feed.getId())
                .mediaUrl(feed.getFeedMedias().get(0).getMediaUrl())
                .isMediaOne(feed.getFeedMedias().size() == 1)
                .likeCount(feedLikeRepository.countLikes(feed.getId()))
                .commentCount(commentRepository.countByFeedId(feed.getId()))
                .build();
    }
}
