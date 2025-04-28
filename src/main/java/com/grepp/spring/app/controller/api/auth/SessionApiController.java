package com.grepp.spring.app.controller.api.auth;

import com.grepp.spring.app.model.auth.CustomUserDetail;
import com.grepp.spring.app.model.user.dto.Principal;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Map<String, Object>> sessionInfo(HttpSession session) {
        Principal principal = (Principal) session.getAttribute("principal");
        String userId = (String) session.getAttribute("userId");

        if (principal == null || userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("role", principal.roles());
        return ResponseEntity.ok(result);
    }
}