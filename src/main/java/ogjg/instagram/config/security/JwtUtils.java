package ogjg.instagram.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {
    private static final int MINUTE = 60 * 1000;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;

    private static final int ACCESS_TOKEN_VALID_TIME = MINUTE * 5;
    private static final long REFRESH_TOKEN_VALID_TIME = DAY * 30L;
    private static String JWT_SECRET;

    @Value("${jwt.secret}")
    private void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

    public static String generateAccessToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer("team_ogjg")
                .setClaims(new HashMap<>(Map.of("email", userDetails.getUser().getEmail())))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))
                .signWith(generateKey())
                .compact();
    }

    public static String generateRefreshToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer("team_ogjg")
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .signWith(generateKey())
                .compact();
    }

    private static Key generateKey() {
        byte[] byteSecretKey = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(byteSecretKey, SignatureAlgorithm.HS256.getJcaName());
    }

}
