package com.information.center.questionservice.model.response;
import com.information.center.questionservice.entity.Answer;
import com.information.center.questionservice.model.QuestionDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private List<AnswerResponse> answers;

    private String externalId;

    private String topicExternalId;

    private String name;

    private QuestionDifficulty questionDifficulty;

    private Date startDate;

}
