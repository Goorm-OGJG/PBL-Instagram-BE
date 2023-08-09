package ogjg.instagram.follow.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.follow.response.FollowResponse;
import ogjg.instagram.follow.response.FollowedResponse;
import ogjg.instagram.follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/follow/{followId}")
    public ResponseEntity<Void> follow(@PathVariable Long followId, Long userId){
//        todo 토큰을 통해 로그인중인 userId를 받아올 예정
        followService.follow(userId,followId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/follow/{followId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followId, Long userId){

//        todo 토큰을 통해 로그인중인 userId를 받아올 예정
        followService.unfollow(userId,followId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/follow/following")
    public ResponseEntity<List<FollowResponse>> following(Long userId){

//        todo 토큰을 통해 로그인중인 userId를 받아올 예정, 프로필 다 만들어지면 id로 조인해서 프로필도 같이 보내줘야한다
        List<FollowResponse> followList = followService.FollowingList(1L);

        return new ResponseEntity<>(followList,HttpStatus.OK);
    }

    @PostMapping("/api/follow/follower")
    public ResponseEntity<List<FollowedResponse>> follower(Long userId){

//        todo 토큰을 통해 로그인중인 userId를 받아올 예정, 프로필 다 만들어지면 id로 조인해서 프로필도 같이 보내줘야한다
        List<FollowedResponse> followedResponses = followService.followedList(2L);

        return new ResponseEntity<>(followedResponses,HttpStatus.OK);
    }



}
