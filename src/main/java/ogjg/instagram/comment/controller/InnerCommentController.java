package ogjg.instagram.comment.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.dto.request.InnerCommentCreateRequestDto;
import ogjg.instagram.comment.service.InnerCommentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
            @PathVariable("commentId") Long commentId
    ) {
        Long jwt_myId = 1L;

        innerCommentService.write(jwt_myId, commentId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 피드 대댓글 삭제
     */
    @DeleteMapping("/inner-comments/{innerCommentId}")
    public ResponseEntity<?> deleteInnerComment(
            @PathVariable("innerCommentId") Long innerCommentId

    ) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                innerCommentService.delete(jwt_myId, innerCommentId)
        );
    }
}
