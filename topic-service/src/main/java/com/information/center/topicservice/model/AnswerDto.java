package com.information.center.topicservice.model;

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
public class AnswerDto {

    private String name;

    private String externalId;

    private QuestionDto question;

    private boolean isCorrect;

    private String reason;
}
