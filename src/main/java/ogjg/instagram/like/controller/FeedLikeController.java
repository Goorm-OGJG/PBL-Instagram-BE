package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.like.dto.feedLike.FeedLikeUserResponse;
import ogjg.instagram.like.service.FeedLikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedLikeController {

    private final FeedLikeService feedLikeService;

    @PostMapping("/{feedId}/like")
    public void feedLike(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        feedLikeService.feedLike(userDetails.getUserId(), feedId);
    }

    @DeleteMapping("/{feedId}/like")
    public void feedUnlike(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        feedLikeService.feedUnlike(feedId, userDetails.getUserId());
    }

    @GetMapping("/{feedId}/likeUser")
    public ResponseEntity<List<FeedLikeUserResponse>> feedLikeUserList(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails ,
            @PageableDefault(page = 0, size = 100) Pageable pageable
    ){
        List<FeedLikeUserResponse> feedLikeUserList = feedLikeService.feedLikeList(feedId, userDetails.getUserId(), pageable);
        return ResponseEntity.ok(feedLikeUserList);
    }

}
