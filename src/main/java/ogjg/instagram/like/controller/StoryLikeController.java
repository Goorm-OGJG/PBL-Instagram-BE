package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.like.service.StoryLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/story")
public class StoryLikeController {

    private final StoryLikeService storyLikeService;

    @PostMapping("/{storyId}/like")
    public void StoryLike(
            @PathVariable("storyId") Long storyId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        storyLikeService.storyLike(userDetails.getUserId(), storyId);
    }

    @DeleteMapping("/{storyId}/like")
    public void StoryUnlike(
            @PathVariable("storyId") Long storyId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        storyLikeService.storyUnlike(storyId, userDetails.getUserId());
    }


}
