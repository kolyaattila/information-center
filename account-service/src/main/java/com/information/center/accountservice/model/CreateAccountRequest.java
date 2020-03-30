package com.information.center.accountservice.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class CreateAccountRequest {

  @Tolerate
  public CreateAccountRequest() {
  }

  private String firstName;
  private String lastName;
  private Date birthday;
  private String photo;
  private String uid;
  @NotNull
  @Size(min = 3, max = 255)
  private String username;

  @NotNull
  @Size(min = 8, max = 15)
  private String password;

  @Override
  public String toString() {
    return "CreateAccountRequest{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", birthday=" + birthday +
        ", photo='" + photo + '\'' +
        ", uid='" + uid + '\'' +
        ", username='" + username + '\'' +
        '}';
  }
}


