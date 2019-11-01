package com.information.center.questionservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {


    private String name;

    private String externalId;

    private String questionExternalId;

    private boolean isCorrect;

    private boolean checked;

    private String reason;
}