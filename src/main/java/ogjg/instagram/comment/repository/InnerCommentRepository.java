package ogjg.instagram.comment.repository;

import ogjg.instagram.comment.domain.InnerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InnerCommentRepository extends JpaRepository<InnerComment ,Long> {

    @Query("""
            select ic from InnerComment  ic 
            join fetch ic.comment c 
            join fetch ic.user u 
            where ic.comment.id = :commentId
                    """)
    List<InnerComment> findAllById(@Param("commentId") Long commentId);

    Long countByCommentId(Long commentId);
}
