package ogjg.instagram.feed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.feed.service.CollectionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/collections", produces = MediaType.APPLICATION_JSON_VALUE)
public class CollectionController {

    public static final long FIXED_COLLECTION_ID = 1L;
    private final CollectionService collectionService;

    /**
     * 피드 보관함 추가
     */
    @PostMapping("/{feedId}")
    public ResponseEntity<?> addCollection(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();
        log.info("loginId = {}", loginId);

        collectionService.collectFeed(feedId, FIXED_COLLECTION_ID, loginId);
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 보관함에서 삭제
     */
    @DeleteMapping("/{feedId}")
    public ResponseEntity<?> deleteCollection(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();
        log.info("loginId = {}", loginId);

        collectionService.deleteFeed(feedId, FIXED_COLLECTION_ID, loginId); // todo : collectionId 상수처리
        return ResponseEntity.ok().build();
    }
}
