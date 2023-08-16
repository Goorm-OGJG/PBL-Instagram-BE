package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.like.dto.commentLike.CommentLikeUserResponse;
import ogjg.instagram.like.service.CommentLikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}/like")
    public void commentLike(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        commentLikeService.commentLike(userDetails.getUserId(), commentId);
    }

    @DeleteMapping("/{commentId}/like")
    public void commentUnlike(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        commentLikeService.commentUnlike(commentId, userDetails.getUserId());
    }

    @GetMapping("/{commentId}/likesUser")
    public ResponseEntity<List<CommentLikeUserResponse>> commentLikeUserList(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PageableDefault(page = 0, size = 100)
            Pageable pageable
    ){
        List<CommentLikeUserResponse> feedLikeUserList = commentLikeService.commentLikeList(commentId, userDetails.getUserId(), pageable);
        return ResponseEntity.ok(feedLikeUserList);
    }

}
