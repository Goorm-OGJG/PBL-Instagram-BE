package ogjg.instagram.config.security;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (username.contains("@")) {
            user = usersRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("올바르지 않은 계정정보 입니다."));
        } else {
            user = usersRepository.findByNickname(username)
                    .orElseThrow(() -> new UsernameNotFoundException("올바르지 않은 계정정보 입니다."));
        }
        return new CustomUserDetails(user);
    }
}
