package com.information.center.accountservice.model;

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

  @NotNull
  private String uid;

  @Override
  public String toString() {
    return "EmailSubscriptionRequest{" +
        "to='" + to + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", uid='" + uid + '\'' +
        '}';
  }
}
