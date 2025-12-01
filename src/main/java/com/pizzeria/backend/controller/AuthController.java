package com.pizzeria.backend.controller;

import com.pizzeria.backend.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );

    String token = jwtUtil.generateToken(auth.getName(), auth.getAuthorities());
    List<String> roles = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(a -> a.startsWith("ROLE_") ? a.substring(5) : a) // normaliza
        .toList();

    return ResponseEntity.ok(Map.of(
        "username", auth.getName(),
        "token", token,
        "roles", roles
    ));
  }

  // Endpoint de diagnóstico para ver lo que ve el backend
  @GetMapping("/me")
  public Map<String, Object> me(Authentication auth) {
    List<String> roles = (auth != null)
        ? auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        : List.of();
    String user = (auth != null) ? auth.getName() : null;
    return Map.of("username", user, "roles", roles);
  }
}
