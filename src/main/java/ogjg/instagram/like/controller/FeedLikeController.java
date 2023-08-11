package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.like.dto.feedLike.FeedLikeUserResponse;
import ogjg.instagram.like.service.FeedLikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeedLikeController {

    private final FeedLikeService feedLikeService;

    @PostMapping("/api/feed/{feedId}/like")
    public void feedLike(@PathVariable("feedId") Long feedId, Long userId){
//       todo token 에서 유저 Id 꺼내요기
        feedLikeService.feedLike(userId,feedId);
    }

    @DeleteMapping("/api/feed/{feedId}/like")
    public void feedUnlike(@PathVariable("feedId") Long feedId, Long userId){
//       todo token에서 유저id꺼내오기
        feedLikeService.feedUnlike(feedId,userId);
    }

    @GetMapping("/api/feed/{feedId}/likeUser")
    public ResponseEntity<FeedLikeUserResponse> feedLikeUserList(
            @PathVariable("feedId") Long feedId,
            Long userId , @PageableDefault(page = 0, size = 100) Pageable pageable){
//        todo token으로 받아올 것
        List<FeedLikeUserResponse> feedLikeUserList = feedLikeService.feedLikeList(feedId, 3L, pageable);

        return new ResponseEntity(feedLikeUserList, HttpStatus.OK);
    }

}
