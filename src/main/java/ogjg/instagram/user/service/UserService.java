package ogjg.instagram.user.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.profile.dto.request.ProfileImgEditRequestDto;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id="+ userId));
    }

    @Transactional
    public User save(Long userId, ProfileImgEditRequestDto requestDto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + userId));

        findUser.editProfile(requestDto);
        return userRepository.save(findUser);
    }

    @Transactional
    public void saveImg(Long userId, String imgUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + userId));

        user.editProfileImg(imgUrl);
    }
}
