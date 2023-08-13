package ogjg.instagram.like.repository;

import ogjg.instagram.like.domain.commentLike.CommentLike;
import ogjg.instagram.like.domain.feedLike.FeedLike;
import ogjg.instagram.like.dto.commentLike.CommentLikeUserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long>{

    @Modifying
    @Query("delete from CommentLike cl where cl.commentLikePK.commentId =:commentId and cl.commentLikePK.userId= :userId")
    void deleteCommentLike(@Param("commentId") Long commentId, @Param("userId") Long userId);

    @Query("""
        select new ogjg.instagram.like.dto.commentLike.CommentLikeUserResponse(c.id, u.id, u.nickname, u.userImg, u.userIntro)
        from Comment c 
        join CommentLike cl on c.id = cl.commentLikePK.commentId 
        join User u on u.id = cl.commentLikePK.userId 
        where c.id = :commentId
        order by cl.createdAt asc
    """)
    List<CommentLikeUserResponse> commentLikeUserList(@Param("commentId") Long commentId, Pageable pageable);

    @Query("select count(*) from CommentLike cl where cl.commentLikePK.commentId = :commentId")
    Long countLikes(@Param("commentId") Long commentId);

    @Query("""
            select cl from CommentLike cl where cl.comment.id = :commentId and cl.user.id = :userId  
    """)
    Optional<CommentLike> checkLikeStatus(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
