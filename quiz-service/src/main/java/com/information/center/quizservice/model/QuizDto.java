package com.information.center.quizservice.model;


import com.information.center.quizservice.entity.QuizType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.util.List;

@Getter
@Setter
@Builder
public class QuizDto {

    @Tolerate
    public QuizDto() {
    }

    private List<String> questionIds;
    private QuizType type;
    private String chapterExternalId;
    private String courseExternalId;
    private Boolean enable;
    private String schoolExternalId;
    private String externalId;
}
