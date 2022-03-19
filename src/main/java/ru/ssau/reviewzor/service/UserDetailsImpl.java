package ru.ssau.reviewzor.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ssau.reviewzor.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private boolean isActive;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            final Long id,
            final String username,
            final String name,
            final String surname,
            final boolean isActive,
            final String password,
            final Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.isActive = isActive;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.isActive(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        //TODO Not implemented
        log.warn("Not implemented method: {}.isAccountNonExpired()", this.getClass().getName());
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //TODO Not implemented
        log.warn("Not implemented method: {}.isAccountNonLocked()", this.getClass().getName());
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //TODO Not implemented
        log.warn("Not implemented method: {}.isCredentialsNonExpired()", this.getClass().getName());
        return true;
    }

    @Override
    public boolean isEnabled() {
        //TODO Not implemented
        log.warn("Not implemented method: {}.isEnabled()", this.getClass().getName());
        return true;
    }
}
