package ogjg.instagram.config.security;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtAuthenticationFilter;
import ogjg.instagram.config.security.jwt.JwtAuthenticationProvider;
import ogjg.instagram.config.security.login.LoginAuthSuccessHandler;
import ogjg.instagram.config.security.login.LoginAuthenticationFilter;
import ogjg.instagram.config.security.login.LoginAuthenticationProvider;
import ogjg.instagram.user.repository.UserAuthenticationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserAuthenticationRepository authenticationRepository;

    private final List<String> permitJwtUrlList = new ArrayList<>(
            List.of("/api/users/signup",
                       "/api/users/token"
            ));

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .addFilterBefore(globalFilterExceptionHandler(), ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginAuthenticationFilter(), JwtAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest)
                        .permitAll()
                        .requestMatchers("/**")
                        .permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://goorm-ogjg.github.io"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthFilter = new LoginAuthenticationFilter(authenticationManager(authenticationConfiguration), loginAuthSuccessHandler());
        authenticationManagerBuilder.authenticationProvider(loginAuthenticationProvider());
        loginAuthFilter.afterPropertiesSet();
        return loginAuthFilter;
    }

    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider() {
        return new LoginAuthenticationProvider(passwordEncoder());
    }

    @Bean
    public LoginAuthSuccessHandler loginAuthSuccessHandler() {
        return new LoginAuthSuccessHandler(authenticationRepository);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(
                authenticationManager(authenticationConfiguration), permitJwtUrlList);
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider());
        jwtAuthFilter.afterPropertiesSet();
        return jwtAuthFilter;
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    @Bean
    public CustomGlobalFilterExceptionHandler globalFilterExceptionHandler() {return new CustomGlobalFilterExceptionHandler();}
}
