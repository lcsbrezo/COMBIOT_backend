package com.iot.combiot.combiot.auth.controller;

import com.iot.combiot.combiot.auth.dto.AuthResponse;
import com.iot.combiot.combiot.auth.dto.LoginRequest;
import com.iot.combiot.combiot.auth.dto.RegisterRequest;
import com.iot.combiot.combiot.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
