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
        .authorizeHttpRequests((req)->req
            .requestMatchers("/api/signin","/api/signup","/","/api/guest-signin").permitAll()
            .requestMatchers("/assets/**", "/resources/**", "/webapp/**").permitAll()
            .requestMatchers("/orders/admin").hasRole(Roleform.ADMIN.getRole())
            .requestMatchers("/api/products","/api/products/**","/product/detail","/api/new-products").permitAll()
            .requestMatchers("/signin").permitAll()
            .requestMatchers("/signup").permitAll()
            .requestMatchers("/api/cart/**").authenticated()
            .requestMatchers("/orders").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin((form)->form
            .loginPage("/user/signin")
            .usernameParameter("userId")
            .loginProcessingUrl("/api/signin")
            .successHandler(customLoginSuccessHandler())
            .failureHandler(authFailHandler))
        .rememberMe(rememberMe -> rememberMe.key("remember-me"))
        .logout((logout)->logout.permitAll()
            .logoutSuccessUrl("/"))
        .sessionManagement(session -> {
          session.maximumSessions(1);
          session.invalidSessionUrl("/");}).csrf(csrf->csrf.disable());

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
