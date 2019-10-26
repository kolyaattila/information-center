package com.information.center.questionservice.model.request;
import com.information.center.questionservice.entity.Answer;
import com.information.center.questionservice.entity.QuestionDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

    private String name;

    private String externalId;

    private List<AnswerRequest> answers;

    private QuestionDifficulty questionDifficulty;

    private String topicExternalId;
}
