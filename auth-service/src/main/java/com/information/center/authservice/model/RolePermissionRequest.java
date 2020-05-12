package com.information.center.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RolePermissionRequest {
    @Tolerate
    public RolePermissionRequest() {
    }

    private String name;
    private String description;
    private List<String> permissions;

}
