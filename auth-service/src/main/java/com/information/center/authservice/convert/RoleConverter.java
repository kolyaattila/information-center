package com.information.center.authservice.convert;

import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.model.RoleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleConverter {

  RoleEntity toRole(RoleRequest roleRequest);
}
