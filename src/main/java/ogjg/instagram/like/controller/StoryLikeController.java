package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.like.service.StoryLikeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoryLikeController {

    private final StoryLikeService storyLikeService;

    @PostMapping("/api/story/{storyId}/like")
    public void StoryLike(@PathVariable("storyId") Long storyId, Long userId){
        storyLikeService.storyLike(userId,storyId);
    }
    @DeleteMapping("/api/story/{storyId}/like")
    public void StoryUnlike(@PathVariable("storyId") Long storyId, Long userId){
        storyLikeService.storyUnlike(storyId, userId);
    }


}
