package com.information.center.topicservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
