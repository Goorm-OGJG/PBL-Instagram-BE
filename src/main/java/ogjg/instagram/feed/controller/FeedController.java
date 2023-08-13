package ogjg.instagram.feed.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.dto.request.FeedCreateRequestDto;
import ogjg.instagram.feed.service.FeedService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedController {
    private final FeedService feedService;

    /**
     * 피드 목록 가져오기
     */
    @GetMapping("")
    public ResponseEntity<?> feedList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
            ) {
        Long jwt_myId = 1L;
        return ResponseEntity.ok(feedService.findFeedList(jwt_myId, pageable));
    }

    /**
     * 피드 상세페이지
     */
    @GetMapping("/{feedId}")
    public ResponseEntity<?> feedDetail(
            @PathVariable("feedId") Long feedId
    ) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(feedService.findDetail(feedId, jwt_myId));
    }

    //todo: crud 완료 후 id 등 내려주기
    /**
     * 피드 작성
     */
    @PostMapping("")
    public ResponseEntity<?> writeFeed(@RequestBody FeedCreateRequestDto requestDto) {
        Long jwt_myId = 1L;

        feedService.write(jwt_myId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 삭제
     */
    @DeleteMapping("/{feedId}")
    public ResponseEntity<?> deleteFeed(
            @PathVariable Long feedId) {
        Long jwt_myId = 1L;

        feedService.delete(jwt_myId, feedId);
        return ResponseEntity.ok().build();
    }
}
