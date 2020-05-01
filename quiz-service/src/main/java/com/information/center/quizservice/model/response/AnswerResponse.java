package com.information.center.quizservice.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class AnswerResponse {

  @Tolerate
  public AnswerResponse() {
  }

  private String externalId;

  private String name;

  private String questionExternalId;

  private boolean isCorrect;

  private boolean checked;

  private String reason;
}
