package com.example.demo_java6_asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            // Clean up role string (remove ROLE_ prefix if present, though In-Memory
            // usually adds it)
            if (role.contains("ROLE_ADMIN"))
                role = "ADMIN";
            else if (role.contains("ROLE_STAFF"))
                role = "STAFF";

            Map<String, Object> response = new HashMap<>();
            // For Basic Auth/In-Memory, we don't have a real JWT.
            // We'll return a dummy token or just the Basic Auth header value for the
            // frontend to use?
            // The frontend expects a 'token'.
            // Let's return the Basic Auth header value as the token so the frontend can use
            // it.
            String token = "Basic "
                    + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

            response.put("token", token);
            response.put("role", role);
            response.put("name", username);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
