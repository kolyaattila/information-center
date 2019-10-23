package com.information.center.topicservice.model.response;

import com.information.center.topicservice.model.QuestionDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private String name;

    private QuestionDifficulty questionDifficulty;

    private TopicResponse topic;
}
