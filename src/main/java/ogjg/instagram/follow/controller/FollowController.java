package ogjg.instagram.follow.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.follow.response.FollowResponse;
import ogjg.instagram.follow.response.FollowedResponse;
import ogjg.instagram.follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followId}")
    public ResponseEntity<Void> follow(
            @PathVariable Long followId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){

        followService.follow(userDetails.getUserId(), followId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> unfollow(
            @PathVariable Long followId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        followService.unfollow(userDetails.getUserId(), followId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followId}/follower")
    public ResponseEntity<Void> followerListDelete(
            @PathVariable Long followId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        followService.unfollow(followId, userDetails.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowResponse>> following(
            @PathVariable("userId") Long userId
    ){
        List<FollowResponse> followList = followService.followingList(userId);
        return new ResponseEntity<>(followList,HttpStatus.OK);
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<FollowedResponse>> follower(
            @PathVariable("userId") Long userId
    ){
        List<FollowedResponse> followedResponses = followService.followedList(userId);
        return new ResponseEntity<>(followedResponses,HttpStatus.OK);
    }
}
