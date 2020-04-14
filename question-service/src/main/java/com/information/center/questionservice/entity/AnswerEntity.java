package com.information.center.questionservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Answer")
@Getter
@Setter
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false, length = 30)
    private String externalId;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    private QuestionEntity question;

    @Column(name = "correct")
    private boolean correct;

    @Column(name = "reason", length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "key", length = 2, nullable = false)
    private AnswerKey key;

    @Column(name = "question_number")
    private int questionNumber;

    @Column(name = "book", length = 100)
    private String book;

    @Column(name = "created")
    private Date created = new Date();
}
