package ogjg.instagram.search.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.follow.service.FollowService;
import ogjg.instagram.hashtag.domain.HashtagFeed;
import ogjg.instagram.hashtag.service.HashtagFeedService;
import ogjg.instagram.search.dto.response.SearchHashtagResponseDto;
import ogjg.instagram.search.dto.response.SearchNicknameResponseDto;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final HashtagFeedService hashtagFeedService;
    private final FollowService followService;

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
                                followService.isFollowing(loginId, user.getId())))
                        .collect(Collectors.toUnmodifiableList()),
                isUser);
    }

    private String wildCard(String search) {
        return "%" + search.trim() + "%";
    }
}
