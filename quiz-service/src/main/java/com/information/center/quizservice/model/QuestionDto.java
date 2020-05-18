package com.information.center.quizservice.model;

import com.information.center.quizservice.entity.QuestionDifficulty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

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
    private boolean verified;
    private int questionNumber;
    private String book;
    private String chapterExternalId;
    private String courseExternalId;
    private String schoolExternalId;
}
