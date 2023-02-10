package com.dh.digitalbooking.data;

import com.dh.digitalbooking.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDetailsData implements UserDetails {

    private final Optional<UserEntity> user;

    public UserDetailsData(Optional<UserEntity> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.ifPresent(userEntity -> authorities.add(new SimpleGrantedAuthority(userEntity.getRole().getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.orElse(new UserEntity()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new UserEntity()).getEmail();
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
        return user.orElse(new UserEntity()).getEnabled();
    }
}
