package com.grepp.spring.app.controller.api.auth;

import com.grepp.spring.app.model.auth.CustomUserDetail;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
    public ResponseEntity<Map<String, Object>> sessionInfo(Authentication authentication, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        // 🔥 세션(userId, role) 먼저 체크 (비회원 먼저 체크)
        String userId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId != null && role != null) {
            result.put("userId", userId);
            result.put("role", List.of(role));
            return ResponseEntity.ok(result);
        }

        // 🔥 그 다음, 인증(Authentication) 체크 (회원)
        if (authentication != null && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken)) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail userDetail) {
                result.put("userId", userDetail.getUsername());
                result.put("role", userDetail.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
                return ResponseEntity.ok(result);
            }
        }

        // 🔥 둘 다 아니면 -> 로그인 안 된 상태
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }




}