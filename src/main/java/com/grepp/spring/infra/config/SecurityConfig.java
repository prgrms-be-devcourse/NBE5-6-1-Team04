package com.grepp.spring.infra.config;


import static org.springframework.http.HttpMethod.GET;

import com.grepp.spring.app.controller.web.auth.form.Roleform;
import com.grepp.spring.infra.config.handler.AuthFailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .requestMatchers("/signin","/signup","/","/guest-signin").permitAll()
                .requestMatchers("/orders/admin").hasRole(Roleform.ADMIN.getRole())
                .requestMatchers("api/products","products","/api/new-products").permitAll()

                .anyRequest().authenticated()

            )
        .formLogin((form)->form
//            .loginPage("/signin")
            .usernameParameter("userId")
            .loginProcessingUrl("/signin")
//            .defaultSuccessUrl("/",true)
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
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();

  }



}
