package com.information.center.topicservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    private String name;

    private String externalId;

    private QuestionDto question;

    private boolean isCorrect;

    private String reason;
}
