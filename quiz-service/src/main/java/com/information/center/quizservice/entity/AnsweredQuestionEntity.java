package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Answered_question")
public class AnsweredQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answered_question_generator")
    @SequenceGenerator(name = "answered_question_generator", sequenceName = "S_ANSWERED_QUESTION_0", allocationSize = 1)
    private long id;

    @Column(name = "external_id", nullable = false, length = 50)
    private String externalId;

    @Column(name = "created")
    private Date created = new Date();

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="solved_quiz_id")
    private SolvedQuizEntity solvedQuiz;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "answer2answered_question",
            joinColumns = @JoinColumn(name = "answered_question_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id"))
    private Collection<AnswerEntity> answerEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private QuestionEntity questionEntity;
}
