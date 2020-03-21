package com.information.center.authservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PasswordUpdateRequest {

  @NotNull
  @Size(min = 3, max = 255)
  private String username;

  @NotNull
  @Size(min = 8, max = 15)
  private String password;

  @NotNull
  @Size(min = 8, max = 15)
  private String oldPassword;

}
