package com.information.center.topicservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {

    private String externalId;

    private String name;

    private QuestionResponse question;

    private boolean isCorrect;

    private String reason;
}
