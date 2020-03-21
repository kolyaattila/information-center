package com.information.center.authservice.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.ErrorResponse;

@Getter
@Setter
@Builder
public class PermissionRequest {

  private String name;
  private String description;
  private List<String> roles;

}
