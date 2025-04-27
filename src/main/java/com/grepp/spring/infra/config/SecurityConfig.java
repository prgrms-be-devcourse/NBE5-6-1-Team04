package com.grepp.spring.infra.config;

import com.grepp.spring.app.model.auth.Code.authenum.Roleform;
import com.grepp.spring.infra.config.handler.AuthFailHandler;
import com.grepp.spring.infra.config.handler.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final AuthFailHandler authFailHandler;

  public SecurityConfig(AuthFailHandler authFailHandler) {
    this.authFailHandler = authFailHandler;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http
            .authorizeHttpRequests((auth) -> auth
                    .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler customLoginSuccessHandler() {
    return new CustomLoginSuccessHandler();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

}