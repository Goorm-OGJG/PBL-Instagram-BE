package ogjg.instagram.like.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.repository.FeedRepository;
import ogjg.instagram.follow.repository.FollowRepository;
import ogjg.instagram.like.domain.feedLike.FeedLike;
import ogjg.instagram.like.dto.feedLike.FeedLikeDto;
import ogjg.instagram.like.dto.feedLike.FeedLikeUserResponse;
import ogjg.instagram.like.repository.FeedLikeRepository;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedLikeService {


    private final FeedLikeRepository feedLikeRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void feedLike(Long userId,Long feedId ){
//        todo 토큰 userId로 변경하기
        FeedLikeDto feedLikeDto = new FeedLikeDto(feedId,userId);
        feedLikeRepository.save(new FeedLike(feedLikeDto,userFindByUserId(userId), feedFindByFeedId(feedId)));
    }

    @Transactional
    public void feedUnlike(Long feedId, Long userId){
//        todo 토큰 userId로 변경하기
        feedLikeRepository.deleteFeedLike(feedId, userId);
    }

    public List<FeedLikeUserResponse> feedLikeList(Long feedId, Long userId, Pageable pageable){
//        todo 토큰 userId로 변경하기
        return feedLikeRepository.feedLikeUserList(feedId, pageable).stream()
                .map(feedLikeUserResponse -> feedLikeUserResponse.putFollowStatus(
                        followRepository.followerMeToo(userId , feedLikeUserResponse.getUserId())== null))
                .toList();
    }

    public Long feedLikeCount(Long feedId){
        return feedLikeRepository.countLikes(feedId);
    }

    private User userFindByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException(userId + ": 사용자를 찾을 수 없습니다"));
    }

    private Feed feedFindByFeedId(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(()->new IllegalArgumentException(feedId + ": 피드를 찾을 수 없습니다"));
    }

}
