package com.information.center.quizservice.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;


@Builder
@Getter
@Setter
public class SchoolRequest {

    @Tolerate
    public SchoolRequest() {
    }

    private String externalId;
    @NotBlank
    private String name;
    private int numberOfQuestions;
}
