package com.information.center.quizservice.model.request;

import com.information.center.quizservice.entity.QuestionDifficulty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class QuestionRequest {

    @Tolerate
    public QuestionRequest() {
    }

    private String name;
    private String externalId;
    private List<AnswerRequest> answers;
    private QuestionDifficulty questionDifficulty;
    private String topicExternalId;
    private boolean verified;
    private int questionNumber;
    private String book;
    private String chapterExternalId;
    private String courseExternalId;
    private String schoolExternalId;
}
