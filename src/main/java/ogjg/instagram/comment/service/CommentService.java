package ogjg.instagram.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.comment.dto.request.CommentCreateRequestDto;
import ogjg.instagram.comment.repository.CommentRepository;
import ogjg.instagram.comment.repository.InnerCommentRepository;
import ogjg.instagram.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final InnerCommentRepository innerCommentRepository;

    @Transactional(readOnly = true)
    public List<InnerComment> findInnerComments(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("답글을 달기 위한 댓글이 존재하지 않습니다. id = " + commentId));
        return innerCommentRepository.findAllById(commentId);
    }

    @Transactional
    public void write(Long loginId, Long feedId, CommentCreateRequestDto requestDto) {
        commentRepository.save(Comment.from(loginId, feedId, requestDto.getContent()));
    }

    @Transactional
    public void delete(Long userId, Long commentId) {
        if (notMyComment(userId, commentId)) throw new IllegalArgumentException("본인의 답글만 삭제할 수 있습니다.");  // todo : 다른사람 답글 삭제 불가 : 에러핸들링

        commentRepository.deleteById(commentId);
    }

    private boolean notMyComment(Long userId, Long commentId) {
        User findUser = findById(commentId).getUser();
        return findUser.getId() != userId;
    }

    @Transactional(readOnly = true)
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 댓글이 존재하지 않습니다. id=" + commentId));
    }

    @Transactional(readOnly = true)
    public Long countByFeedId(Long feedId) {
        return commentRepository.countByFeedId(feedId);
    }

    @Transactional(readOnly = true)
    public Long countTotalComment(Long feedId) {
        List<Long> includeIds = commentRepository.findIncludeIds(feedId);
        Long commentCount = countByFeedId(feedId);
        Long innerCommentCount = commentRepository.countByCommentIds(includeIds);

        log.info("commentCount ={}", commentCount);
        log.info("innerCommentCount ={}", innerCommentCount);
        return commentCount + innerCommentCount;
    }
}
