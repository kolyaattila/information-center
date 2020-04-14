package com.information.center.questionservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @OneToMany(
            mappedBy = "question",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<AnswerEntity> answers;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private QuestionDifficulty questionDifficulty;

    @Column(name = "topic_id", nullable = false)
    private String topicExternalId;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "question_number")
    private int questionNumber;

    @Column(name = "book", length = 100)
    private String book;

    @Column(name = "chapter", length = 100)
    private String chapter;

    @Column(name = "created")
    private Date created = new Date();
}
