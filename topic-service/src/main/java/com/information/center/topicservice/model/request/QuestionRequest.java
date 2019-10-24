package com.information.center.topicservice.model.request;

import com.information.center.topicservice.entity.Answer;
import com.information.center.topicservice.entity.Topic;
import com.information.center.topicservice.model.QuestionDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

    private String name;

    private List<Answer> answers;

    private QuestionDifficulty questionDifficulty;

    private TopicRequest topic;
}
