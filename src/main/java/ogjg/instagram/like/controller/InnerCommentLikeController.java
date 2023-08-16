package ogjg.instagram.like.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.like.service.InnerCommentLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/innerComment")
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

}
