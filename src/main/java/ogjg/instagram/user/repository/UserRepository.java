package ogjg.instagram.user.repository;

import ogjg.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
