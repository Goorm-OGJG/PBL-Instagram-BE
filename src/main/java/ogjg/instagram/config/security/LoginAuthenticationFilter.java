package ogjg.instagram.config.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final LoginAuthSuccessHandler loginAuthSuccessHandler;
    private final List<String> permitUrlList;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isUrlPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode requestBody = objectMapper.readTree(request.getInputStream());
        String username = requestBody.get("username").asText();
        String password = requestBody.get("password").asText();
        String type = requestBody.get("type").asText();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        loginAuthSuccessHandler.onAuthenticationSuccess(request, response, authentication, type);

    }

    private boolean isUrlPath(HttpServletRequest request) {
        return permitUrlList.stream()
                .anyMatch(url -> url.equals(request.getRequestURI()));
    }
}
