package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.like.dto.commentLike.CommentLikeUserResponse;
import ogjg.instagram.like.service.CommentLikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;


    @PostMapping("/api/comment/{commentId}/like")
    public void commentLike(@PathVariable("commentId") Long commentId, Long userId){
//       todo token 에서 유저 Id 꺼내요기
        commentLikeService.commentLike(userId,commentId);
    }

    @DeleteMapping("/api/comment/{commentId}/like")
    public void commentUnlike(@PathVariable("commentId") Long commentId, Long userId){
//       todo token에서 유저id꺼내오기
        commentLikeService.commentUnlike(commentId, userId);
    }

    @GetMapping("/api/comment/{commentId}/likesUser")
    public ResponseEntity<CommentLikeUserResponse> commentLikeUserList(
            @PathVariable("commentId") Long commentId,
            Long userId,
            @PageableDefault(page = 0, size = 100)
            Pageable pageable){
//        todo token으로 받아올 것
        List<CommentLikeUserResponse> feedLikeUserList = commentLikeService.commentLikeList(commentId, 3L, pageable);

        return new ResponseEntity(feedLikeUserList, HttpStatus.OK);
    }



}
