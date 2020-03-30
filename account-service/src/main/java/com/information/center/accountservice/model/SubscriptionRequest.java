package com.information.center.accountservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class SubscriptionRequest {

  @Tolerate
  public SubscriptionRequest() {
  }

  @NotNull
  @Length(min = 5, max = 255)
  @Email
  private String email;
  @NotNull
  @Length(min = 3, max = 255)
  private String firstName;
  @NotNull
  @Length(min = 3, max = 255)
  private String lastName;


  @Override
  public String toString() {
    return "SubscriptionRequest{" +
        "email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
