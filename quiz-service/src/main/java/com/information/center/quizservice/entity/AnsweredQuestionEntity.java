package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Answered_question")
public class AnsweredQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false, length = 50)
    private String externalId;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userExternalId;

    @Column(name = "correct_answer", nullable = false)
    private boolean correctAnswer;

    @Column(name = "created")
    private Date created = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="solved_quiz_id")
    private SolvedQuizEntity solvedQuiz;

}
