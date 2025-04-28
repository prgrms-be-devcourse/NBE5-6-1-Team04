package com.grepp.spring.infra.config;

import com.grepp.spring.app.model.auth.Code.authenum.Roleform;
import com.grepp.spring.app.model.auth.CustomUserDetail;
import com.grepp.spring.app.model.auth.CustomUserService;
import com.grepp.spring.app.model.user.dto.User;
import com.grepp.spring.app.model.auth.AuthRepository;
import com.grepp.spring.infra.config.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.grepp.spring.app.model.auth")
@EnableWebSecurity
public class SecurityConfig {


  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((req) -> req
            .requestMatchers("/api/signin", "/signup", "/api/signup", "/", "/user/signin", "/guest-signin", "/user/guest-signin", "/users", "/api/guest-signin").permitAll()
            .requestMatchers("/assets/**", "/resources/**", "/webapp/**").permitAll()
            .requestMatchers("/admin").hasRole(Roleform.ADMIN.getRole())
            .requestMatchers("/api/products", "/api/products/**", "/api/new-products").permitAll()
            .requestMatchers("/product/**").permitAll()
            .requestMatchers("/cart/**").authenticated()
            .requestMatchers("/api/orders").authenticated()
            .requestMatchers("/api/orders/guest").permitAll()
            .requestMatchers("/api/session-info").permitAll()
            .requestMatchers("/cart").authenticated()
            .requestMatchers("/my-page/**").authenticated()
            .anyRequest().authenticated()
        )

        .rememberMe(remember -> remember
            .rememberMeParameter("remember-me")
            .tokenValiditySeconds(60 * 60 * 24 * 7)
            .key("1234")
        )
        .formLogin(form -> form
            .loginPage("/user/signin")
            .loginProcessingUrl("/api/signin")
            .usernameParameter("userId")
            .passwordParameter("password")
            .successHandler(new CustomLoginSuccessHandler())
            .defaultSuccessUrl("/",true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
            .permitAll()
        )
        .csrf(csrf -> csrf.disable());

    return http.build();
  }



  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
