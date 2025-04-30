package com.grepp.spring.app.model.auth;

import com.grepp.spring.app.model.user.dto.User;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomUserDetail implements UserDetails {

  private User userDto;



  public CustomUserDetail(User userDto) {
    this.userDto = userDto;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userDto.getRole().name()));
  }

  @Override
  public String getPassword() {
    return userDto.getPassword();
  }

  @Override
  public String getUsername() {
    return userDto.getUserId();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;}

  @Override
  public boolean isAccountNonLocked() {
    return true; }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;}

  @Override
  public boolean isEnabled() {
    return true;
  }
}
