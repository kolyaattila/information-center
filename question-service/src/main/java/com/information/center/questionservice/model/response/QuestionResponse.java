package com.information.center.questionservice.model.response;

import com.information.center.questionservice.entity.QuestionDifficulty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class QuestionResponse {

    @Tolerate
    public QuestionResponse() {
    }

    private List<AnswerResponse> answers;

    private String externalId;

    private String name;

    private QuestionDifficulty questionDifficulty;

    private String topicExternalId;

}
