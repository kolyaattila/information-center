package com.information.center.questionservice.model.response;
import com.information.center.questionservice.entity.Answer;
import com.information.center.questionservice.model.QuestionDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private List<AnswerResponse> answers;

    private String externalId;

    private String name;

    private QuestionDifficulty questionDifficulty;

    private String topicExternalId;
}
