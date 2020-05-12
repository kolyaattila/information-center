package com.information.center.authservice.convert;

import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.model.RoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleConverter {
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRole(RoleRequest roleRequest);

    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRole(RoleRequest roleRequest, long id);
}
