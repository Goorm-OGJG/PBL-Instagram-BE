package ogjg.instagram.search.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.follow.service.FollowService;
import ogjg.instagram.hashtag.service.HashtagFeedService;
import ogjg.instagram.hashtag.service.HashtagService;
import ogjg.instagram.search.dto.SearchHashTagCountDto;
import ogjg.instagram.search.dto.response.SearchHashtagResponseDto;
import ogjg.instagram.search.dto.response.SearchHashtagResultResponseDto;
import ogjg.instagram.search.dto.response.SearchNicknameResponseDto;
import ogjg.instagram.search.repository.SearchRepository;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final HashtagFeedService hashtagFeedService;
    private final FollowService followService;
    private final SearchRepository searchRepository;

    //todo : slice 알아보기
    @Transactional(readOnly = true)
    public SearchHashtagResponseDto searchByHashtag(boolean isUser, String searchKey, Pageable pageable) {
        return SearchHashtagResponseDto.from(
                hashtagFeedService.findByHashtagContaining(wildCard(searchKey), pageable)
                        .stream()
                        .map(this::toSearchHashtagDto)
                        .toList(),
                isUser
        );

    }

    private SearchHashtagResponseDto.SearchHashtagDto toSearchHashtagDto(Object[] searchResult) {
        return  SearchHashtagResponseDto.SearchHashtagDto.of(
                (String) searchResult[0],
                (Long) searchResult[1]
//                hashtagFeedService.countTaggedFeeds(content)
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
                        .collect(toUnmodifiableList()),
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

        List<Feed> findFeed = searchRepository.findFeedByContent(content, pageable);
        Map<Long, SearchHashTagCountDto> countMap = searchRepository.findFeedLikeCountAndCommentCount(findFeed)
                .stream()
                .collect(toMap(
                        (dto) -> dto.getFeedId(),
                        (dto) -> dto)
                );

        String thumbnail = findFeed.get(0).getFeedMedias().get(0).getMediaUrl();

        return SearchHashtagResultResponseDto.from(
                findFeed.stream()
                        .map((feed -> toSearchHashtagResultDto(feed, countMap.get(feed.getId()))))
                        .toList(),
                feedCount,
                content,
                thumbnail
        );
    }

    private SearchHashtagResultResponseDto.SearchHashtagResultDto toSearchHashtagResultDto(Feed feed, SearchHashTagCountDto countDto) {
        return SearchHashtagResultResponseDto.SearchHashtagResultDto.builder()
                .feedId(feed.getId())
                .mediaUrl(feed.getFeedMedias().get(0).getMediaUrl())
                .isMediaOne(feed.getFeedMedias().size() == 1)
                .likeCount(countDto.getLikeCount())
                .commentCount(countDto.getCommentCount())
                .build();
    }
}
