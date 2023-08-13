package ogjg.instagram.feed.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.service.CollectionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/collections", produces = MediaType.APPLICATION_JSON_VALUE)
public class CollectionController {

    private final CollectionService collectionService;

    /**
     * 피드 보관함 추가
     */
    @PostMapping("/{feedId}")
    public ResponseEntity<?> addCollection(
            @PathVariable("feedId") Long feedId
    ) {
        Long jwt_myId = 1L;

        collectionService.collectFeed(feedId, jwt_myId);
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 보관함에서 삭제
     */
    @DeleteMapping("/{feedId}")
    public ResponseEntity<?> deleteCollection(
            @PathVariable("feedId") Long feedId
    ) {
        Long jwt_myId = 1L;

        collectionService.deleteFeed(feedId, 0L, jwt_myId); // todo : collectionId 상수처리
        return ResponseEntity.ok().build();
    }
}
