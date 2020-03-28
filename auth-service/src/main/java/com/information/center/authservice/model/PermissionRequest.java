package com.information.center.authservice.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class PermissionRequest {

  @Tolerate
  public PermissionRequest() {
  }

  private String name;
  private String description;
  private List<String> roles;

}
