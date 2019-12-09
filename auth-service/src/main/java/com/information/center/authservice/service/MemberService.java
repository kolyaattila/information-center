package com.information.center.authservice.service;

import com.information.center.authservice.config.MicroserviceException;
import com.information.center.authservice.converter.MemberConverter;
import com.information.center.authservice.model.MemberDto;
import com.information.center.authservice.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberService {

    @Autowired
    private  MemberRepository memberRepository;

    @Autowired
    private  MemberConverter memberConverter;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public MemberDto findUserByName(String name) {
        return memberConverter.toDto(memberRepository.findByUsername(name).orElseThrow(throwNotFoundItem("user",name)));
    }

    public void create(MemberDto memberDto) {

        var user = memberConverter.toEntity(memberDto);
        user.setIdentity(encoder.encode(memberDto.getIdentity()));
        user.setExternalId(UUID.randomUUID().toString());
        memberRepository.save(user);
    }

    public void update(MemberDto memberDto) {
        var member = memberRepository.findByExternalId(memberDto.getExternalId()).orElseThrow(throwNotFoundItem("member",memberDto.getExternalId()));
        var memberPersistent = memberConverter.toEntity(memberDto);
        memberPersistent.setId(member.getId());
        memberRepository.save(memberPersistent);

    }
    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id/name " + itemId);
    }
}