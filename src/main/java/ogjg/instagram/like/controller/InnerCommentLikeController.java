package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.like.dto.commentLike.CommentLikeUserResponse;
import ogjg.instagram.like.service.InnerCommentLikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class InnerCommentLikeController {

    private final InnerCommentLikeService innerCommentLikeService;

    @PostMapping("/api/innerComment/{innerCommentId}/like")
    public void commentLike(@PathVariable("innerCommentId") Long innerCommentId, Long userId){
//       todo token 에서 유저 Id 꺼내요기
        innerCommentLikeService.innerCommentLike(userId,innerCommentId);
    }

    @DeleteMapping("/api/innerComment/{innerCommentId}/like")
    public void commentUnlike(@PathVariable("innerCommentId") Long innerCommentId, Long userId){
//       todo token에서 유저id꺼내오기
        innerCommentLikeService.innerCommentUnlike(innerCommentId,userId);
    }



}
