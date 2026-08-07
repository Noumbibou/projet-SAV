package com.emsi.sav.serviceagents.controllers;

import com.emsi.sav.serviceagents.dto.LoginRequest;
import com.emsi.sav.serviceagents.dto.LoginResponse;
import com.emsi.sav.serviceagents.entities.User;
import com.emsi.sav.serviceagents.repositories.UserRepository;
import com.emsi.sav.serviceagents.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        String token = jwtUtil.genererToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new LoginResponse(token, user.getRole().name()));
    }
}