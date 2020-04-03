package com.information.center.topicservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private List<AnswerResponse> answers;

    private String externalId;

    private String name;

    private QuestionDifficulty questionDifficulty;

    private TopicResponse topic;
}
