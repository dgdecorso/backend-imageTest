package com.example.demo.domain.user;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserDetailsImpl(User user) implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var roleAuthorities = user.getRoles()
        .stream()
        .map(r -> new SimpleGrantedAuthority(r.getName()))
        .toList();

    var authorities = user.getRoles()
        .stream()
        .flatMap(r -> r.getAuthorities().stream())
        .map(a -> new SimpleGrantedAuthority(a.getName()))
        .toList();

    return java.util.stream.Stream.concat(roleAuthorities.stream(), authorities.stream())
        .toList();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
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
