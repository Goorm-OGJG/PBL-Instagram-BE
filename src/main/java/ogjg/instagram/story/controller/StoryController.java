package ogjg.instagram.story.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.story.dto.request.StoryMediaListDto;
import ogjg.instagram.story.dto.response.StoryResponse;
import ogjg.instagram.story.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/story")
public class StoryController {

    private final StoryService storyService;

    @GetMapping("/stories")
    public ResponseEntity<StoryResponse> storyList(
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        StoryResponse storyResponse = new StoryResponse(storyService.storyList(userDetails.getUserId()));
        return ResponseEntity.ok(storyResponse);
    }

    @PostMapping("/{storyId}/read")
    public void storyRead(
            @PathVariable("storyId") Long storyId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        storyService.storyReadSave(storyId, userDetails.getUserId());
    }

    @PostMapping
    public void storySave(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody StoryMediaListDto storyMedia
    ){
        storyService.storySave(userDetails.getUserId(), storyMedia);
    }

    @DeleteMapping("{storyId}")
    public void storyDelete(
            @PathVariable("storyId") Long storyId
    ){
        storyService.storyDelete(storyId);
    }

}
