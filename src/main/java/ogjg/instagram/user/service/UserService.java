package ogjg.instagram.user.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id="+ userId));
    }
}
