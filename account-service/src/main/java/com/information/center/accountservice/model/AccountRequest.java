package com.information.center.accountservice.model;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class AccountRequest {

  @Tolerate
  public AccountRequest() {
  }

  private String firstName;
  private String lastName;
  private Date birthday;
  private String photo;
  private String uid;

  @Override
  public String toString() {
    return "AccountRequest{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", birthday=" + birthday +
        ", photo='" + photo + '\'' +
        ", uid='" + uid + '\'' +
        '}';
  }
}
