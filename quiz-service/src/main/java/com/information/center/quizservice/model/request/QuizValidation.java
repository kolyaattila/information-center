package com.information.center.quizservice.model.request;

import com.information.center.quizservice.model.QuestionDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizValidation {

    private List<QuestionDto> questions;
    @NotBlank
    private String externalId;
    @NotBlank
    private String userExternalId;

    private double note;
    private boolean passed;
}
