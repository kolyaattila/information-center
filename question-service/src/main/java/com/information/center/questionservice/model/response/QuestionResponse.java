package com.information.center.questionservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {

    private List<AnswerResponse> answers;

    private String externalId;

    private String topicExternalId;

    private String name;

    private String questionDifficulty;

    private Date startDate;

}
