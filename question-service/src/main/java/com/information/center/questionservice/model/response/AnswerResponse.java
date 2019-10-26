package com.information.center.questionservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {

    private String externalId;

    private String name;

    private String questionExternalId;

    private boolean isCorrect;

    private boolean checked;

    private String reason;
}
