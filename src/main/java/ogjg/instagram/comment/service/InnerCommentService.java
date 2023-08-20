package ogjg.instagram.comment.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.comment.dto.request.InnerCommentCreateRequestDto;
import ogjg.instagram.comment.dto.response.InnerCommentListResponseDto;
import ogjg.instagram.comment.repository.InnerCommentRepository;
import ogjg.instagram.like.service.InnerCommentLikeService;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InnerCommentService {
    private final UserService userService;
    private final CommentService commentService;
    private final InnerCommentRepository innerCommentRepository;
    private final InnerCommentLikeService innerCommentLikeService;

    @Transactional
    public void write(Long userId, Long commentId, InnerCommentCreateRequestDto requestDto) {
        User user = userService.findById(userId);
        Comment findComment = commentService.findById(commentId);

        innerCommentRepository.save(InnerComment.builder()
                .user(user)
                .comment(findComment)
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Transactional
    public Long delete(Long userId, Long innerCommentId) {
        if (notMyInnerComment(userId, innerCommentId)) throw new IllegalArgumentException("본인의 답글만 삭제할 수 있습니다.");  // todo : 다른사람 답글 삭제 불가 : 에러핸들링

        InnerComment deletedComment = innerCommentRepository.findById(innerCommentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글 입니다."));

        innerCommentRepository.deleteById(innerCommentId);
        return deletedComment.getId();
    }

    private boolean notMyInnerComment(Long userId, Long innerCommentId) {
        User findUser = findById(innerCommentId).getUser();
        return findUser.getId() != userId;
    }

    @Transactional(readOnly = true)
    public InnerComment findById(Long innerCommentId) {
        return innerCommentRepository.findById(innerCommentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답글입니다. id =" + innerCommentId));
    }

    @Transactional(readOnly = true)
    public InnerCommentListResponseDto innerCommentList(Long commentId, Long userId) {
        return InnerCommentListResponseDto.builder()
                .commentId(commentId)
                .innerComments(
                        commentService.findInnerComments(commentId).stream()
                                .map(toInnerCommentListDto(userId))
                                .collect(Collectors.toUnmodifiableList())
                )
                .build();
    }

    private Function<InnerComment, InnerCommentListResponseDto.InnerCommentListDto> toInnerCommentListDto(Long userId) {
        return innerComment ->
                InnerCommentListResponseDto.InnerCommentListDto.from(
                        innerComment,
                        innerCommentLikeService.likeStatus(userId, innerComment.getId()),
                        innerCommentLikeService.innerCommentLikeCount(innerComment.getId())
                );
    }
}
