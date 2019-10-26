package com.information.center.questionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    private String externalId;

    private String name;

    private String questionExternalId;

    private boolean isCorrect;

    private boolean checked;

    private String reason;
}
