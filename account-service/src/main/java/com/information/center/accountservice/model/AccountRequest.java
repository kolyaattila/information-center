package com.information.center.accountservice.model;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class AccountRequest {

  @Tolerate
  public AccountRequest() {
  }

  private String username;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  private Date birthday;
  private String photo;
  @NotBlank
  private String uid;

  @Override
  public String toString() {
    return "AccountRequest{" +
        "username='" + username + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", birthday=" + birthday +
        ", photo='" + photo + '\'' +
        ", uid='" + uid + '\'' +
        '}';
  }
}
