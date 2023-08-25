package ogjg.instagram.user.repository;

import ogjg.instagram.user.domain.UserAuthenticationNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticationNumberRepository extends JpaRepository<UserAuthenticationNumber, Long> {
    Optional<UserAuthenticationNumber> findByAuthenticationCode(String authenticationCode);
}
