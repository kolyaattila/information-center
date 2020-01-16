package com.information.center.emailservice.model;

import javax.validation.constraints.Email;
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
  @NotNull
  private String to;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

}
