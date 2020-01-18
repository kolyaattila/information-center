package com.information.center.emailservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class EmailSubscriptionRequest {

  @Tolerate
  public EmailSubscriptionRequest() {
  }

  @Email
  @NotEmpty(message = "to cannot be empty.")
  @NotNull(message = "to must not be null")
  private String to;

  @NotNull(message = "firstName must not be null")
  @NotEmpty(message = "firstName cannot be empty.")
  private String firstName;

  @NotNull(message = "lastName must not be null")
  @NotEmpty(message = "lastName cannot be empty.")
  private String lastName;

  @NotNull(message = "uid must not be null")
  @NotEmpty(message = "uid cannot be empty.")
  private String uid;
}
