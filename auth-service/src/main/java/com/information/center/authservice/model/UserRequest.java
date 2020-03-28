package com.information.center.authservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class UserRequest {

  @Tolerate
  public UserRequest() {
  }

  @NotNull
  @Size(min = 3, max = 255)
  private String username;

  @NotNull
  @Size(min = 8, max = 15)
  private String password;

}
