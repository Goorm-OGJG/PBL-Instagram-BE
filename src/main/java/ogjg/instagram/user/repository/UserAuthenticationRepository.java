package ogjg.instagram.user.repository;

import ogjg.instagram.user.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {
    Optional<UserAuthentication> findByUsername(String username);

    Optional<UserAuthentication> findByNickname(String nickname);
}
