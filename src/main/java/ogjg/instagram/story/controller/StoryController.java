package ogjg.instagram.story.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.story.dto.request.StoryMediaListDto;
import ogjg.instagram.story.dto.response.StoryResponse;
import ogjg.instagram.story.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/story")
public class StoryController {

    private final StoryService storyService;

    @GetMapping("/stories")
    public ResponseEntity<StoryResponse> storyList(Long userId){
//        todo 로그인사용자 아이디
        StoryResponse storyResponse = new StoryResponse(storyService.storyList(1L));
        return ResponseEntity.ok(storyResponse);
    }

    @PostMapping("/{storyId}/read")
    public void storyRead(@PathVariable("storyId") Long storyId, Long userId){
//        todo 로그인사용자 아이디
        storyService.storyReadSave(storyId,userId);
    }

    @PostMapping
    public void storySave(
            Long userId,
            @RequestBody StoryMediaListDto storyMedia
                          ){
//        todo 로그인사용자 아이디
        storyService.storySave(10L, storyMedia);
    }

    @DeleteMapping("{storyId}")
    public void storyDelete(@PathVariable("storyId") Long storyId, Long userId){
//        todo 로그인 사용자 아이디
        storyService.storyDelete(storyId, userId);
    }

}
