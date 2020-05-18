package com.information.center.quizservice.model.request;

import com.information.center.quizservice.entity.QuestionDifficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class FilterQuestionRequest {

    @Tolerate
    public FilterQuestionRequest() {
    }

    private String name;
    private int questionNumber;
    private QuestionDifficulty questionDifficulty;
    private String chapterExternalId;
    private String courseExternalId;
    private String externalId;
    private String book;
    private boolean verified;
    @NotNull
    private int pageNumber;
    @NotNull
    private int pageSize;
}
