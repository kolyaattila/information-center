package com.information.center.authservice.convert;

import com.information.center.authservice.entity.PermissionEntity;
import com.information.center.authservice.model.PermissionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionConverter {

  PermissionEntity toPermission(PermissionRequest permissionRequest);
}
