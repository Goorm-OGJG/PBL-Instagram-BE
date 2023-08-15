package ogjg.instagram.comment.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.dto.request.InnerCommentCreateRequestDto;
import ogjg.instagram.comment.service.InnerCommentService;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class InnerCommentController {

    private final InnerCommentService innerCommentService;

    /**
     * 피드 대댓글 작성
     */
    @PostMapping("/comments/{commentId}/inner-comment")
    public ResponseEntity<?> writeInnerComment(
            @RequestBody InnerCommentCreateRequestDto requestDto,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();


        innerCommentService.write(loginId, commentId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 대댓글 삭제
     */
    @DeleteMapping("/inner-comments/{innerCommentId}")
    public ResponseEntity<?> deleteInnerComment(
            @PathVariable("innerCommentId") Long innerCommentId,
            @AuthenticationPrincipal JwtUserDetails userDetails

    ) {
        Long loginId = userDetails.getUserId();


        return ResponseEntity.ok(
                innerCommentService.delete(loginId, innerCommentId)
        );
    }
}
