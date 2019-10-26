package com.information.center.questionservice.entity;

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

   @OneToMany( cascade = CascadeType.ALL)
    private List<Answer> answers;

    private String name;

    @Enumerated(EnumType.STRING)
    private QuestionDifficulty  questionDifficulty;

    private String topicExternalId;
}
