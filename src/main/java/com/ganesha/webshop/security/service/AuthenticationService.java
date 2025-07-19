package com.ganesha.webshop.security.service;

import com.ganesha.webshop.controller.AuthController;
import com.ganesha.webshop.model.dto.request.LoginRequest;
import com.ganesha.webshop.model.dto.response.JwtResponse;
import com.ganesha.webshop.model.entity.user.Member;
import com.ganesha.webshop.repository.MemberRepository;
import com.ganesha.webshop.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.memberRepository = memberRepository;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        logger.info("Authenticating user " + loginRequest.username() + " with password " + loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Member member = memberRepository.findByUsername(loginRequest.username()).orElseThrow();
        return new JwtResponse(jwt, member.getUsername(), member.getRole().getName());
    }

}
