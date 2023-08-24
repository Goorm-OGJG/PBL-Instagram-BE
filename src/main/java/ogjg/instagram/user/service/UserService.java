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
import ogjg.instagram.user.dto.AuthenticationNumberRequestDto;
import ogjg.instagram.user.dto.JwtUserClaimsDto;
import ogjg.instagram.user.dto.SignupRequestDto;
import ogjg.instagram.user.repository.UserAuthenticationRepository;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

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
                .userImg("https://pbl-insta-image.s3.ap-northeast-2.amazonaws.com/images/default_img.png")
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

    @Transactional
    public boolean isEmailAlreadyInUse(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public boolean isNicknameAlreadyInUse(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    private static String getAccessToken(UserAuthentication userAuth) {
        JwtUserClaimsDto userClaimsDto = JwtUserClaimsDto.builder()
                .userId(userAuth.getUserId())
                .username(userAuth.getUsername())
                .nickname(userAuth.getNickname())
                .build();
        return generateAccessToken(userClaimsDto);
    }

    private UserAuthentication getUserAuthentication(String refreshToken) {
        Claims claims = getClaims(refreshToken);
        String username = (String) claims.get("email");

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

    @Transactional
    public void logout(String username, HttpServletResponse response) {
        deleteRefreshToken(username);
        deleteRefreshTokenCookie(response);
    }

    private void deleteRefreshToken(String username) {
        Optional<UserAuthentication> userAuth = authenticationRepository.findByUsername(username);
        userAuth.ifPresent(authenticationRepository::delete);
    }

    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", null)
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }

    @Transactional(readOnly = true)
    public User findMemberIfExists(AuthenticationNumberRequestDto authenticationNumberRequestDto) {
        if ("email".equals(authenticationNumberRequestDto.getType())) {
            return userRepository.findByEmail(authenticationNumberRequestDto.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        }
        if ("nickname".equals(authenticationNumberRequestDto.getType())) {
            return userRepository.findByNickname(authenticationNumberRequestDto.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        }
        throw new IllegalArgumentException("요청 타입이 존재하지 않습니다.");
    }
}
