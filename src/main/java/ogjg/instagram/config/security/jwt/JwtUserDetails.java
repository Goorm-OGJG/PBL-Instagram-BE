package ogjg.instagram.config.security.jwt;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.user.dto.JwtUserClaimsDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtUserDetails implements UserDetails {

    private final JwtUserClaimsDto jwtUserClaimsDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return "";
    }

    public Long getUserId() {
        return jwtUserClaimsDto.getUserId();
    }

    @Override
    public String getUsername() {
        return jwtUserClaimsDto.getUsername();
    }

    public String getNickname() {
        return jwtUserClaimsDto.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
