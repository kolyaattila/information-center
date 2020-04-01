package com.information.center.topicservice.model.request;

import com.information.center.topicservice.model.QuestionDifficulty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

  private String name;

  private List<AnswerRequest> answers;

  private QuestionDifficulty questionDifficulty;

  private TopicRequest topic;
}
