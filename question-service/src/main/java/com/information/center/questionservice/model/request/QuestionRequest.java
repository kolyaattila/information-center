package com.information.center.questionservice.model.request;

import com.information.center.questionservice.entity.QuestionDifficulty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class QuestionRequest {

  @Tolerate
  public QuestionRequest() {
  }

  private String name;

  private String externalId;

  private List<AnswerRequest> answers;

  private String questionDifficulty;

  private String topicExternalId;
}
