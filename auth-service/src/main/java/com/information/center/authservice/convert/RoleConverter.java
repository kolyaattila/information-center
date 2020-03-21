package com.information.center.authservice.convert;

import com.information.center.authservice.entity.Role;
import com.information.center.authservice.model.RoleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleConverter {

  Role toRole(RoleRequest roleRequest);
}
