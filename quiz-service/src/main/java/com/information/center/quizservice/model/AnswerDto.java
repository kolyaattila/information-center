package com.information.center.quizservice.model;

import com.information.center.quizservice.entity.AnswerKey;
import com.information.center.quizservice.entity.AnswerType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class AnswerDto {

    @Tolerate
    public AnswerDto() {
    }

    private String externalId;
    private String name;
    private String questionExternalId;
    private boolean checked;
    private String reason;
    private boolean correct;
    private AnswerKey key;
    private boolean selected;
    private AnswerType answerType;
    private String parseText;
}
