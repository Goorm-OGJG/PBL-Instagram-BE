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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository usersRepository;

    @Transactional
    public void follow(
            Long userId, Long followId
    ){
        FollowResponse followResponse = FollowResponse.builder()
                .followId(followId)
                .userId(userId)
                .build();

//      todo 나중에 User가 생기면 User로 변경하고 삭제해야함
        followRepository.save(new Follow(followResponse, findUser(userId), findUser(followId)));
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

        //      todo 나중에 User가 생기면 User로 변경하고 삭제해야함
        followRepository.delete(new Follow(followResponse, findUser(userId), findUser(followId)));
    }

    private User findUser(Long userId){
        return usersRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다" + userId));
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> FollowingList(Long userId){
        return followRepository.FollowingList(userId);
    }

//    false 는 팔로우를 안함
    @Transactional(readOnly = true)
    public List<FollowedResponse> followedList(Long userId){
        return followRepository.followerList(userId)
                .stream()
                .map( followResponse -> FollowedResponse.builder()
                        .followResponse(followResponse)
                        .followingStatus(
                                followRepository.followerMeToo(
                                        followResponse.getFollowId(),
                                        followResponse.getUserId()) != null
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
    public boolean isFollowing(Long userId, Long jwt_myId) {
        return followRepository.followerMeToo(userId, jwt_myId) == null;
    }

    @Transactional(readOnly = true)
    public List<Long> getFollowedIds(Long id) {
        return followedList(id).stream()
                .map((followedResponse ->  followedResponse.getFollowId()))
                .collect(Collectors.toUnmodifiableList());
    }
}
