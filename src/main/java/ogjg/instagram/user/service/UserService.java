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
import ogjg.instagram.user.domain.UserAuthenticationNumber;
import ogjg.instagram.user.dto.AuthNumberVerificationDto;
import ogjg.instagram.user.dto.JwtUserClaimsDto;
import ogjg.instagram.user.dto.SignupRequestDto;
import ogjg.instagram.user.dto.UserAuthNumberRequestDto;
import ogjg.instagram.user.repository.UserAuthenticationNumberRepository;
import ogjg.instagram.user.repository.UserAuthenticationRepository;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static ogjg.instagram.config.security.jwt.JwtUtils.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthenticationRepository authenticationRepository;
    private final UserAuthenticationNumberRepository authenticationNumberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

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
    public User findMemberIfExists(UserAuthNumberRequestDto userAuthNumberRequestDto) {
        if ("email".equals(userAuthNumberRequestDto.getType())) {
            return userRepository.findByEmail(userAuthNumberRequestDto.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        }
        if ("nickname".equals(userAuthNumberRequestDto.getType())) {
            return userRepository.findByNickname(userAuthNumberRequestDto.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        }
        throw new IllegalArgumentException("요청 타입이 존재하지 않습니다.");
    }

    @Transactional
    public void generateAuthenticationNumber(User user) {
        SecureRandom random = new SecureRandom();
        String authenticationCode = String.valueOf(random.nextInt(900000) + 100000);

        UserAuthenticationNumber userAuthenticationNumber = UserAuthenticationNumber.builder()
                .userId(user.getId())
                .authenticationCode(authenticationCode)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(3))
                .build();

        authenticationNumberRepository.save(userAuthenticationNumber);

        sendVerificationNumberToEmail(user, authenticationCode);
    }

    private void sendVerificationNumberToEmail(User user, String authenticationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("[OGJG] Instagram 인증번호 안내입니다.");
        message.setText("안녕하세요. OGJG입니다. 인증번호는 [" + authenticationCode + "] 입니다.");
        mailSender.send(message);
    }

    @Transactional
    public User isValidAuthNumber(AuthNumberVerificationDto authNumberVerificationDto) {

        UserAuthenticationNumber authNumber = authenticationNumberRepository.findByAuthenticationCode(authNumberVerificationDto.getValidate())
                .orElseThrow(() -> new IllegalArgumentException("유효한 인증번호가 아닙니다."));

        User authNumberUser = userRepository.findById(authNumber.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다.")
        );

        if (!isSameUserAuthentication(authNumberVerificationDto, authNumberUser)) {
            throw new IllegalArgumentException("변경 요청 회원과 인증번호 발급 회원이 일치하지 않습니다.");
        }

        if (authNumber.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }

        return authNumberUser;
    }

    private boolean isSameUserAuthentication(AuthNumberVerificationDto authNumberVerificationDto, User authNumberUser) {
        if ("email".equals(authNumberVerificationDto.getType())) {
            return authNumberUser.getEmail().equals(authNumberVerificationDto.getUsername());
        }
        if ("nickname".equals(authNumberVerificationDto.getType())) {
            return authNumberUser.getNickname().equals(authNumberVerificationDto.getUsername());
        }
        return false;
    }

    public String generateTemporaryToken(User user) {
        JwtUserClaimsDto userClaimsDto = JwtUserClaimsDto.builder()
                .userId(user.getId())
                .username(user.getEmail())
                .nickname(user.getNickname())
                .build();

        return "Bearer " + generateAccessToken(userClaimsDto);
    }
}
