package com.information.center.topicservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {

    private String name;

    private QuestionRequest question;

    private boolean isCorrect;

    private String reason;
}