package com.information.center.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserRoleRequest {

    @Tolerate
    public UserRoleRequest() {
    }

    @NotNull
    @Size(min = 3, max = 255)
    private String username;

    private List<String> roles;
}
