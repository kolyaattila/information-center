package com.information.center.quizservice.model.request;

import com.information.center.quizservice.entity.QuizType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
public class QuizRequest {

    @Tolerate
    public QuizRequest() {
    }

    @NotBlank
    private QuizType type;
    private String chapterExternalId;
    private String courseExternalId;
    private Boolean enable;
    private String schoolExternalId;
    private List<String> questionIds;
    private String externalId;
}
