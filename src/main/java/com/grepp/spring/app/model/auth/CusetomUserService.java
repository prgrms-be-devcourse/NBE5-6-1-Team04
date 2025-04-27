package com.grepp.spring.app.model.auth;

import com.grepp.spring.app.model.AuthRepository;
import com.grepp.spring.app.model.user.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CusetomUserService implements UserDetailsService {

  private final AuthRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = authRepository.selectuserId(username)
        .orElseThrow(()->new UsernameNotFoundException(username));
    log.info(user.getPassword());
    return new CustomUserDetail(user);

  }
}
