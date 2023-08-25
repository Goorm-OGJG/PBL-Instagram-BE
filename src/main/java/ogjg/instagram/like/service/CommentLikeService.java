package ogjg.instagram.like.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.comment.domain.Comment;
import ogjg.instagram.comment.repository.CommentRepository;
import ogjg.instagram.follow.repository.FollowRepository;
import ogjg.instagram.like.domain.commentLike.CommentLike;
import ogjg.instagram.like.dto.commentLike.CommentLikeDto;
import ogjg.instagram.like.dto.commentLike.CommentLikeUserResponse;
import ogjg.instagram.like.repository.CommentLikeRepository;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void commentLike(Long userId,Long commentId ){
        CommentLikeDto commentLikeDto = new CommentLikeDto(commentId,userId);
        commentLikeRepository.save(
                new CommentLike(commentLikeDto, userFindByUserId(userId), commentFindByCommentId(commentId))
        );
    }

    @Transactional
    public void commentUnlike(Long commentId, Long userId){
        commentLikeRepository.deleteCommentLike(commentId, userId);
    }

    public List<CommentLikeUserResponse> commentLikeList(Long commentId, Long userId, Pageable pageable){
        return commentLikeRepository.commentLikeUserList(commentId, pageable)
                .stream()
                .map(commentLikeUserResponse -> commentLikeUserResponse.putFollowStatus(
                        followRepository.isFollowingUser(userId , commentLikeUserResponse.getUserId())== null))
                .toList();
    }

    public Long commentLikeCount(Long commentId){
        return commentLikeRepository.countLikes(commentId);
    }

    private User userFindByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException(userId + ": 사용자를 찾을 수 없습니다"));
    }

    private Comment commentFindByCommentId(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException(commentId + ": 댓글를 찾을 수 없습니다"));
    }

    public boolean isCommentLiked(Long commentId, Long userId) {
        return commentLikeRepository.checkLikeStatus(commentId, userId).isPresent();
    }
}
