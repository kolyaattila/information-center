package com.information.center.quizservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Builder
@Getter
@Setter
public class SchoolDto {

    @Tolerate
    public SchoolDto() {
    }

    private String externalId;
    private String name;
    private int numberOfQuestions;
}
