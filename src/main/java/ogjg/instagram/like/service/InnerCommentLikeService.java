package ogjg.instagram.like.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.domain.InnerComment;
import ogjg.instagram.comment.repository.InnerCommentRepository;
import ogjg.instagram.like.domain.innerCommentLike.InnerCommentLike;
import ogjg.instagram.like.dto.innerCommentLike.InnerCommentLikeDto;
import ogjg.instagram.like.repository.InnerCommentLikeRepository;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InnerCommentLikeService {

    private final InnerCommentLikeRepository innerCommentLikeRepository;
    private final UserRepository userRepository;
    private final InnerCommentRepository innerCommentRepository;

    @Transactional
    public void innerCommentLike(Long userId,Long innerCommentId ){
        InnerCommentLikeDto innerCommentLikeDto= new InnerCommentLikeDto(innerCommentId,userId);
        innerCommentLikeRepository.save(
                new InnerCommentLike(innerCommentLikeDto, userFindByUserId(userId), innerCommentFindById(innerCommentId))
        );
    }

    @Transactional
    public void innerCommentUnlike(Long innerCommentId, Long userId){
        innerCommentLikeRepository.deleteInnerCommentLike(innerCommentId, userId);
    }

    @Transactional(readOnly = true)
    public Long innerCommentLikeCount(Long innerCommentId){
        return innerCommentLikeRepository.countLikes(innerCommentId);
    }

    private User userFindByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException(userId + ": 사용자를 찾을 수 없습니다"));
    }

    private InnerComment innerCommentFindById(Long innerCommentId){
        return innerCommentRepository.findById(innerCommentId)
                .orElseThrow(() -> new IllegalArgumentException(innerCommentId + ": 대댓글를 찾을 수 없습니다"));
    }

    @Transactional(readOnly = true)
    public Long countInnerComment(Long commentId) {
        return innerCommentRepository.countByCommentId(commentId);
    }

    @Transactional(readOnly = true)
    public boolean likeStatus(Long userId, Long innerCommentId) {
        return innerCommentLikeRepository.likeStatus(userId, innerCommentId).isPresent();
    }
}
