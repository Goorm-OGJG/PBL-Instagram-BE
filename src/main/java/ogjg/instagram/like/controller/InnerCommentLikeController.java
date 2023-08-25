package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.like.dto.innerCommentLike.InnerCommentLikeUserResponse;
import ogjg.instagram.like.service.InnerCommentLikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inner-comment")
public class InnerCommentLikeController {

    private final InnerCommentLikeService innerCommentLikeService;

    @PostMapping("/{innerCommentId}/like")
    public void commentLike(
            @PathVariable("innerCommentId") Long innerCommentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        innerCommentLikeService.innerCommentLike(userDetails.getUserId(), innerCommentId);
    }

    @DeleteMapping("/{innerCommentId}/like")
    public void commentUnlike(
            @PathVariable("innerCommentId") Long innerCommentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ){
        innerCommentLikeService.innerCommentUnlike(innerCommentId, userDetails.getUserId());
    }

    @GetMapping("/{innerCommentId}/likes-user")
    public ResponseEntity<List<InnerCommentLikeUserResponse>> commentLikeUserList(
            @PathVariable("innerCommentId") Long innerCommentId,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PageableDefault(page = 0, size = 100)
            Pageable pageable
    ){
        List<InnerCommentLikeUserResponse> feedLikeUserList = innerCommentLikeService.innerCommentLikeList(innerCommentId, userDetails.getUserId(), pageable);
        return ResponseEntity.ok(feedLikeUserList);
    }

}
