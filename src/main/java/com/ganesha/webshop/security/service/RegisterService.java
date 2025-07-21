package com.ganesha.webshop.security.service;

import com.ganesha.webshop.model.dto.request.RegisterRequest;
import com.ganesha.webshop.model.dto.response.RegisterResponse;
import com.ganesha.webshop.model.entity.user.Member;
import com.ganesha.webshop.model.entity.user.Role;
import com.ganesha.webshop.model.exception.HandleEmailExistException;
import com.ganesha.webshop.model.exception.HandleUserNameExistException;
import com.ganesha.webshop.repository.MemberRepository;
import com.ganesha.webshop.repository.RoleRepository;
import com.ganesha.webshop.service.mapper.NewMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    private final MemberRepository memberRepository;
    private final NewMemberMapper newMemberMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegisterService(MemberRepository memberRepository, NewMemberMapper newMemberMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.newMemberMapper = newMemberMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public RegisterResponse addMember(RegisterRequest registerRequest) {
        if (memberRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new HandleUserNameExistException();
        }

        if (memberRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new HandleEmailExistException();
        }

        try {
            Role role = roleRepository.findByName("USER").orElseThrow(() -> new IllegalArgumentException("Default USER role not found."));
            Member member = newMemberMapper.mapToEntity(registerRequest);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRole(role);
            memberRepository.save(member);
            return new RegisterResponse(member.getUsername(), member.getEmail());
        } catch (Exception e) {
            logger.error("Error registering user", e);
            throw new RuntimeException("Error registering user");
        }
    }
}
