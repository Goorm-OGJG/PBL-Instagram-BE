package ogjg.instagram.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.profile.dto.request.ProfileEditRequestDto;
import ogjg.instagram.profile.dto.request.ProfileImgEditRequestDto;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.domain.UserAuthentication;
import ogjg.instagram.user.dto.JwtUserClaimsDto;
import ogjg.instagram.user.dto.SignupRequestDto;
import ogjg.instagram.user.repository.UserAuthenticationRepository;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static ogjg.instagram.config.security.jwt.JwtUtils.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id="+ userId));
    }

    @Transactional
    public User save(Long userId, ProfileEditRequestDto requestDto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + userId));

        findUser.editProfile(requestDto);
        return userRepository.save(findUser);
    }

    @Transactional
    public void saveImg(Long userId, ProfileImgEditRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + userId));

        user.editProfileImg(requestDto.getImgUrl());
    }

    @Transactional
    public ResponseEntity<?> registerUser(SignupRequestDto signupRequestDto) {
        User user = User.builder()
                .email(signupRequestDto.getEmail())
                .userName(signupRequestDto.getUsername())
                .nickname(signupRequestDto.getNickname())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build();
        userRepository.save(user);
        return new ResponseEntity<>("가입 성공", HttpStatus.OK);
    }

    @Transactional
    public void generateToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = getRefreshToken(request);

        if (!isValidToken(refreshToken)) {
            throw new JwtException("유효한 Refresh Token이 아닙니다.");
        }

        UserAuthentication userAuth = getUserAuthentication(refreshToken);

        if (refreshToken.equals(userAuth.getRefreshToken())) {
            String accessToken = getAccessToken(userAuth);
            response.setHeader("Authorization", "Bearer " + accessToken);
        }
    }

    private static String getAccessToken(UserAuthentication userAuth) {
        JwtUserClaimsDto userClaimsDto = JwtUserClaimsDto.builder()
                .username(userAuth.getUsername())
                .nickname(userAuth.getNickname())
                .build();
        return generateAccessToken(userClaimsDto);
    }

    private UserAuthentication getUserAuthentication(String refreshToken) {
        Claims claims = getClaims(refreshToken);
        String username = (String) claims.get("sub");

        return authenticationRepository.findByUsername(username).orElseThrow(
                () -> new JwtException("Refresh Token을 찾을 수 없습니다."));
    }

    private static String getRefreshToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "RefreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("쿠키에 Refresh Token이 존재하지 않습니다."));
    }
}
