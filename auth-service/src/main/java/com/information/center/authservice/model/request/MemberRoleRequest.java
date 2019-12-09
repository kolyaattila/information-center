package com.information.center.authservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRoleRequest {

    private String externalId;

    @ManyToOne
    private MemberRequest user;

    @ManyToOne
    private RoleRequest role;

}
