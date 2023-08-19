package ogjg.instagram.feed.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.feed.dto.request.FeedCreateRequestDto;
import ogjg.instagram.feed.service.FeedService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal JwtUserDetails userDetails
            ) {
        Long loginId = userDetails.getUserId();
        return ResponseEntity.ok(feedService.findFeedList(loginId, pageable));
    }

    /**
     * 피드 상세페이지
     */
    @GetMapping("/{feedId}")
    public ResponseEntity<?> feedDetail(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();
        return ResponseEntity.ok(feedService.findDetail(feedId, loginId));
    }

    /**
     * 피드 작성
     */
    @PostMapping("")
    public ResponseEntity<?> writeFeed(@RequestBody FeedCreateRequestDto requestDto,
                                       @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        feedService.write(loginId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 삭제
     */
    @DeleteMapping("/{feedId}")
    public ResponseEntity<?> deleteFeed(
            @PathVariable Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        feedService.delete(loginId, feedId);
        return ResponseEntity.ok().build();
    }
}
