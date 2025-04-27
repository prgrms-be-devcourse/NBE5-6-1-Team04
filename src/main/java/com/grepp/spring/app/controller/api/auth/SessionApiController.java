package com.grepp.spring.app.controller.api.auth;

import com.grepp.spring.app.model.auth.CustomUserDetail;
import com.grepp.spring.app.model.user.dto.Principal;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SessionApiController {

    @GetMapping("/session-info")
    public ResponseEntity<Map<String, Object>> sessionInfo(
        Authentication authentication
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String userId = userDetail.getUsername();

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("roles", authentication.getAuthorities());

        return ResponseEntity.ok(result);
    }
}