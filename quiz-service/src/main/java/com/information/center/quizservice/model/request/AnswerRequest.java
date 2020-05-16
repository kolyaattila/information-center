package com.information.center.quizservice.model.request;

import com.information.center.quizservice.entity.AnswerKey;
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
  private boolean correct;
  private String reason;
  private AnswerKey key;
}
