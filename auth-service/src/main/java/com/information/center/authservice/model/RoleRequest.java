package com.information.center.authservice.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class RoleRequest {

  @Tolerate
  public RoleRequest() {
  }

  private String name;
  private String description;
  private List<String> permissions;
}
