package com.information.center.emailservice.model;

import java.util.List;
import java.util.Map;
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
  private String from;

  @Email
  @NotNull
  private String to;

  @NotNull
  private String subject;

  private List<Object> attachments;
  private Map<String, Object> model;

}
