package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Solved_quiz")
public class SolvedQuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solved_quiz_generator")
    @SequenceGenerator(name = "solved_quiz_generator", sequenceName = "S_SOLVED_QUIZ_0", allocationSize = 1)
    private long id;

    @Column(name = "external_id", nullable = false, length = 50)
    private String externalId;

    @Column(name = "created")
    private Date created = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    private QuizEntity quiz;

    @Column(name = "passed")
    private boolean passed;

    @Column(name = "note")
    private double note;

    @Column(name = "completed_quiz")
    private boolean completedQuiz;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userExternalId;

    @OneToMany(mappedBy = "solvedQuiz", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<AnsweredQuestionEntity> answeredQuestionEntities = new ArrayList<>();
}
