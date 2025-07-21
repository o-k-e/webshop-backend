package com.ganesha.webshop.controller;

import com.ganesha.webshop.model.dto.request.LoginRequest;
import com.ganesha.webshop.model.dto.request.RegisterRequest;
import com.ganesha.webshop.model.dto.response.JwtResponse;
import com.ganesha.webshop.model.dto.response.RegisterResponse;
import com.ganesha.webshop.security.service.AuthenticationService;
import com.ganesha.webshop.security.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RegisterService registerService;

    public AuthController(AuthenticationService authenticationService, RegisterService registerService) {
        this.authenticationService = authenticationService;
        this.registerService = registerService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return registerService.addMember(registerRequest);
    }
}
