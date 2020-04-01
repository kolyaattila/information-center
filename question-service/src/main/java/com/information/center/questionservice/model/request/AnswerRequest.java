package com.information.center.questionservice.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class AnswerRequest {

  @Tolerate
  public AnswerRequest() {
  }

  private String name;

  private String externalId;

  private String questionExternalId;

  private boolean isCorrect;

  private boolean checked;

  private String reason;
}
