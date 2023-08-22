package ogjg.instagram.comment.repository;

import ogjg.instagram.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByFeedId(Long feedId);

    @Query("""
    select c.id from Comment c 
    join c.feed f
    where f.id = :feedId
""")
    List<Long> findIncludeIds(@Param("feedId") Long feedId);

    //todo : 쿼리문 구조 개선 생각해보기
    @Query("""
    select count(ic.id) from InnerComment ic
    join ic.comment c
    where c.id in :ids
""")
    Long countByCommentIds(@Param("ids") List<Long> includeIds);
}
