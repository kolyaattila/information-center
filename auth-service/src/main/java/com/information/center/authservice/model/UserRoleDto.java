package com.information.center.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

    private String externalId;

    @ManyToOne
    private MemberDto user;

    @ManyToOne
    private RoleDto role;

}
