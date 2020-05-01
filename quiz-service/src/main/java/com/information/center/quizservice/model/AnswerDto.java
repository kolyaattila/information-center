package com.information.center.quizservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class AnswerDto {

  @Tolerate
  public AnswerDto() {
  }

  private String externalId;

  private String name;

  private String questionExternalId;

  private boolean isCorrect;

  private boolean checked;

  private String reason;
}
