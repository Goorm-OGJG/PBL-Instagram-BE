package ogjg.instagram.user.repository;

import ogjg.instagram.user.domain.UserAuthenticationNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthenticationNumberRepository extends JpaRepository<UserAuthenticationNumber, Long> {
}
