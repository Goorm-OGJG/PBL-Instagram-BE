package ogjg.instagram.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import ogjg.instagram.user.dto.JwtUserClaimsDto;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String jwt = (String) authentication.getCredentials();

        if (!JwtUtils.isValidToken(jwt)) {
            throw new JwtException("유효하지 않은 JWT입니다.");
        }

        JwtUserDetails jwtUserDetails = getJwtUserDetails(jwt);

        return new JwtAuthenticationToken(jwtUserDetails, jwt, Collections.emptyList());
    }

    private JwtUserDetails getJwtUserDetails(String jwt) {
        Claims claims = JwtUtils.getClaims(jwt);

        JwtUserClaimsDto userClaimsDto = JwtUserClaimsDto.builder()
                .userId(Long.parseLong((String) claims.get("id")))
                .username((String) claims.get("email"))
                .nickname((String) claims.get("username"))
                .build();

        return new JwtUserDetails(userClaimsDto);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
