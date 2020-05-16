package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Solved_quiz")
public class SolvedQuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private float note;

    @Column(name = "completed_quiz")
    private boolean completedQuiz;
}
