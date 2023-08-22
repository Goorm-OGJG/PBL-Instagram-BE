package ogjg.instagram.comment.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.dto.request.CommentCreateRequestDto;
import ogjg.instagram.comment.service.CommentService;
import ogjg.instagram.comment.service.InnerCommentService;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;
    private final InnerCommentService innerCommentService;

    /**
     * 피드 대댓글 보기 - 전부 내려주기
     */
    @GetMapping("/comments/{commentId}/inner-comments")
    public ResponseEntity<?> innerCommentList(
            @PathVariable ("commentId") Long commentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();
        return ResponseEntity.ok(
                innerCommentService.innerCommentList(commentId, loginId)
        );
    }

    /**
     * 피드 댓글 작성
     */
    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<?> writeComment(
            @RequestBody CommentCreateRequestDto requestDto,
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        commentService.write(loginId, feedId, requestDto); //todo : id 반환받아서 내려주기
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}") // todo : url feedID 불필요, url 정리
    public ResponseEntity<?> deleteInnerComment(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        commentService.delete(loginId, commentId);
        return ResponseEntity.ok().build();
    }
}
