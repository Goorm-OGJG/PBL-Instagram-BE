package ogjg.instagram.follow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.follow.domain.Follow;
import ogjg.instagram.follow.repository.FollowRepository;
import ogjg.instagram.follow.response.FollowResponse;
import ogjg.instagram.follow.response.FollowedResponse;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(
            Long userId, Long followId
    ){
        FollowResponse followResponse = FollowResponse.builder()
                .followId(followId)
                .userId(userId)
                .build();

        followRepository.save(
                new Follow(followResponse, findUser(userId), findUser(followId))
        );
    }

    @Transactional
    public void unfollow(
            Long userId,
            Long followId
    ){
        FollowResponse followResponse = FollowResponse.builder()
                .followId(followId)
                .userId(userId)
                .build();

        followRepository.delete(new Follow(followResponse, findUser(userId), findUser(followId)));
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다" + userId));
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> followingList(Long userId){
        return followRepository.FollowingList(userId);
    }

    @Transactional(readOnly = true)
    public List<FollowedResponse> followedList(Long userId){
        return followRepository.followerList(userId)
                .stream()
                .map( followResponse -> FollowedResponse.builder()
                        .followResponse(followResponse)
                        .followingStatus(
                                followRepository.followerMeToo(
                                        followResponse.getFollowId(),
                                        followResponse.getUserId()
                                ) != null
                        )
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public Long followingCount(Long userId){
        return followRepository.followingCount(userId);
    }

    @Transactional(readOnly = true)
    public Long followedCount(Long userId){
        return followRepository.followerCount(userId);
    }

    @Transactional(readOnly = true)
    public boolean isFollowingUser(Long loginId, Long userId) {
        return followRepository.followerMeToo(loginId, userId) != null;
    }

    @Transactional(readOnly = true)
    public List<Long> getFollowingIds(Long id) {
        return followingList(id).stream()
                .map((FollowResponse::getFollowId))
                .toList();
    }
}
