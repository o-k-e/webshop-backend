package com.ganesha.webshop.security.service;

import com.ganesha.webshop.model.entity.user.Member;
import com.ganesha.webshop.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities("ROLE_" + member.getRole().getName())
                .build();
    }
}
