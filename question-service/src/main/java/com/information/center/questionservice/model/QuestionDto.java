package com.information.center.questionservice.model;

import com.information.center.questionservice.entity.QuestionDifficulty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class QuestionDto {

  @Tolerate
  public QuestionDto() {
  }

  private String externalId;

  private List<AnswerDto> answers;

  private String name;

  private QuestionDifficulty questionDifficulty;

  private String topicExternalId;
}
