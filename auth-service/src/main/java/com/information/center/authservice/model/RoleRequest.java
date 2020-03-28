package com.information.center.authservice.model;

import java.util.List;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class RoleRequest {

  @Tolerate
  public RoleRequest() {
  }

  private String name;
  private String description;
  private List<String> usernames;
}
