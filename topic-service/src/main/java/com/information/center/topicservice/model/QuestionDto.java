package com.information.center.topicservice.model;

import com.information.center.topicservice.entity.Answer;
import com.information.center.topicservice.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private String externalId;

    private List<Answer> answers;

    private String name;

    private QuestionDifficulty questionDifficulty;

    private TopicDto topic;
}
