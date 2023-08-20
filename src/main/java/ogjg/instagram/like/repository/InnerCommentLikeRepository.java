package ogjg.instagram.like.repository;

import ogjg.instagram.like.domain.feedLike.FeedLike;
import ogjg.instagram.like.domain.innerCommentLike.InnerCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InnerCommentLikeRepository extends JpaRepository<InnerCommentLike, Long> {

    @Modifying
    @Query("delete from InnerCommentLike icl where icl.innerCommentLikePK.innerCommentId =:innerCommentId and icl.innerCommentLikePK.userId= :userId")
    void deleteInnerCommentLike(@Param("innerCommentId") Long innerCommentId, @Param("userId") Long userId);
    @Query("select count(*) from InnerCommentLike icl where icl.innerCommentLikePK.innerCommentId = :innerCommentId")
    Long countLikes(@Param("innerCommentId") Long innerCommentId);

    @Query("""
            select icl from InnerCommentLike icl where icl.innerComment.id = :innerCommentId and icl.user.id = :userId  
    """)
    Optional<InnerCommentLike> likeStatus(@Param("userId") Long userId, @Param("innerCommentId") Long innerCommentId);
}
