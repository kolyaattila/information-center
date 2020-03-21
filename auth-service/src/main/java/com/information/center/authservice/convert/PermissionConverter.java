package com.information.center.authservice.convert;

import com.information.center.authservice.entity.Permission;
import com.information.center.authservice.model.PermissionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionConverter {

  Permission toPermission(PermissionRequest permissionRequest);
}
