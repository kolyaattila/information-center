package com.information.center.topicservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String externalId;

    @OneToMany
    private List<Answer> answers;

    private String name;

    private QuestionDifficulty  questionDifficulty;

    @ManyToOne
    private Topic topic;
}
