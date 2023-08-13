package ogjg.instagram.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final int MINUTE = 60 * 1000;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;

    private static final int ACCESS_TOKEN_VALID_TIME = MINUTE * 5;
    private static final long REFRESH_TOKEN_VALID_TIME = DAY * 30L;
    private static final String ISSUER = "team_ogjg";
    private static Key SIGNATURE_KEY;
    private static String JWT_SECRET;

    @Value("${jwt.secret}")
    private void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

    public static String generateAccessToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(userDetails))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))
                .signWith(generateKey())
                .compact();
    }

    public static String generateRefreshToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(userDetails.getUser().getId().toString())
                .setIssuer("team_ogjg")
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .signWith(generateKey())
                .compact();
    }

    public static boolean isValidToken(String jwt) {

        Claims claims = getClaims(jwt);

        if (!ISSUER.equals(claims.get("iss"))) {
            throw new JwtException("Issuer가 일치하지 않습니다.");
        }

        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())) {
            throw new JwtException("토큰이 만료되었습니다.");
        }

        return true;
    }

    public static Claims getClaims(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody();
    }

    private static Map<String, Object> createHeader() {
        return new HashMap<>(Map.of(
                "alg", "HS256",
                "typ", "JWT"
        ));
    }

    private static Map<String, Object> createClaims(CustomUserDetails userDetails) {
        return new HashMap<>(Map.of(
                "iss", ISSUER,
                "email", userDetails.getUsername(),
                "username", userDetails.getNickname()
        ));
    }

    private static Key generateKey() {
        if (SIGNATURE_KEY == null) {
            byte[] byteSecretKey = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
            SIGNATURE_KEY = new SecretKeySpec(byteSecretKey, SignatureAlgorithm.HS256.getJcaName());
        }
        return SIGNATURE_KEY;
    }

}
