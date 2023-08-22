package ogjg.instagram.config.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUtils;
import ogjg.instagram.user.domain.UserAuthentication;
import ogjg.instagram.user.dto.JwtUserClaimsDto;
import ogjg.instagram.user.dto.LoginResponseDto;
import ogjg.instagram.user.repository.UserAuthenticationRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class LoginAuthSuccessHandler {

    private final String AUTH_HEADER = "Authorization";
    private final String REFRESH_TOKEN = "RefreshToken";
    private final String TOKEN_TYPE = "Bearer ";
    private final int EXPIRATION = 60 * 60 * 24 * 30;

    private final UserAuthenticationRepository authenticationRepository;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String type) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        JwtUserClaimsDto userClaimsDto = JwtUserClaimsDto.builder()
                .userId(userDetails.getUser().getId())
                .username(userDetails.getUsername())
                .nickname(userDetails.getNickname())
                .build();

        String accessToken = JwtUtils.generateAccessToken(userClaimsDto);
        String refreshToken = JwtUtils.generateRefreshToken(userClaimsDto);

        Optional<UserAuthentication> userAuth = getUserAuthentication(userDetails, type);

        upsertRefreshTokenToDatabase(userDetails, userAuth, refreshToken);

        response.addHeader(AUTH_HEADER, TOKEN_TYPE + accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Set-Cookie", getRefreshTokenCookie(refreshToken).toString());
        response.setCharacterEncoding("UTF-8");

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .id(userDetails.getUser().getId())
                .nickname(userDetails.getNickname())
                .userImg(userDetails.getUser().getUserImg())
                .build();

        objectMapper.writeValue(response.getWriter(), loginResponseDto);
    }

    private ResponseCookie getRefreshTokenCookie(String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .maxAge(EXPIRATION)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        return cookie;
    }

    private void upsertRefreshTokenToDatabase(CustomUserDetails userDetails, Optional<UserAuthentication> userAuth, String refreshToken) {
        if (userAuth.isPresent()) {
            UserAuthentication updatedUserAuth = userAuth.get();
            updatedUserAuth.updateRefreshToken(refreshToken);
            authenticationRepository.save(updatedUserAuth);
        }
        if (userAuth.isEmpty()) {
            UserAuthentication savedUserAuth = UserAuthentication.builder()
                    .userId(userDetails.getUser().getId())
                    .username(userDetails.getUsername())
                    .nickname(userDetails.getNickname())
                    .refreshToken(refreshToken)
                    .build();
            authenticationRepository.save(savedUserAuth);
        }
    }

    private Optional<UserAuthentication> getUserAuthentication(CustomUserDetails userDetails, String type) {
        if (type.equals("email")) {
            return authenticationRepository.findByUsername(userDetails.getUsername());
        }

        if (type.equals("nickname")) {
            return authenticationRepository.findByNickname(userDetails.getNickname());
        }

        return Optional.empty();
    }

}
