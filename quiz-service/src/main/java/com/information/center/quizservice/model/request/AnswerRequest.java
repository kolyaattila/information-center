package com.information.center.quizservice.model.request;

import com.information.center.quizservice.entity.AnswerKey;
import com.information.center.quizservice.entity.AnswerType;
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
  private AnswerType answerType;
  private String parseText;
}
