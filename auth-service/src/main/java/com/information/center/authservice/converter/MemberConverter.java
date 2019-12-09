package com.information.center.authservice.converter;

import com.information.center.authservice.model.MemberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberConverter {
    MemberDto toDto(com.information.center.authservice.entity.Member member);
    com.information.center.authservice.entity.Member toEntity(MemberDto memberDto);
}
