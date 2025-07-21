package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.request.RegisterRequest;
import com.ganesha.webshop.model.entity.user.Member;
import com.ganesha.webshop.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class NewMemberMapper {

    public Member mapToEntity(RegisterRequest registerRequest) {
        Member member = new Member();
        member.setUsername(registerRequest.username());
        member.setPassword(registerRequest.password());
        member.setFirstName(registerRequest.firstName());
        member.setLastName(registerRequest.lastName());
        member.setEmail(registerRequest.email());
        member.setPhoneNumber(registerRequest.phoneNumber());
        //role set in RegisterService
        return member;
    }
}
