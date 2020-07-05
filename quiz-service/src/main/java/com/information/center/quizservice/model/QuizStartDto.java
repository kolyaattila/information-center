package com.information.center.quizservice.model;

import com.information.center.quizservice.entity.QuizType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizStartDto {

    private List<QuestionDto> questions;
    private QuizType type;
    private String chapterExternalId;
    private String courseExternalId;
    private Boolean enable;
    private String schoolExternalId;
    private String externalId;
    private String description;
}
