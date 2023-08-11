package ogjg.instagram.user.repository;

import ogjg.instagram.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.nickname LIKE :searchKey")
    Page<User> findByNicknameContaining(@Param("searchKey") String searchKey, Pageable pageable);
}
