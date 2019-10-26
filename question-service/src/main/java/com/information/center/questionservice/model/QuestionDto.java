package com.information.center.questionservice.model;
import com.information.center.questionservice.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private String externalId;

    private List<AnswerDto> answers;

    private String name;

    private QuestionDifficulty questionDifficulty;

    private String topicExternalId;
}
