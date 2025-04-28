package com.grepp.spring.app.controller.api.auth;

import com.grepp.spring.app.model.auth.CustomUserDetail;
import org.springframework.security.core.GrantedAuthority;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class SessionApiController {

    @GetMapping("/session-info")
    public ResponseEntity<Map<String, Object>> sessionInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userDetail.getUsername());
        result.put("role", userDetail.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList());

        return ResponseEntity.ok(result);
    }

}