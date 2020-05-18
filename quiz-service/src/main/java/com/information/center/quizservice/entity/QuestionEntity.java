package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
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

    @Column(name = "external_id", nullable = false,length = 50)
    private String externalId;

    @OneToMany(
            mappedBy = "question",
            cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<AnswerEntity> answers;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private QuestionDifficulty questionDifficulty;

    @Column(name = "topic_id", nullable = false, length = 50)
    private String topicExternalId;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "question_number")
    private int questionNumber;

    @Column(name = "book", length = 100)
    private String book;

    @Column(name = "chapter", length = 100)
    private String chapterExternalId;

    @Column(name = "created")
    private Date created = new Date();

    @Column(name = "course_id", nullable = false, length = 50)
    private String courseExternalId;

    @ManyToMany(mappedBy = "questions")
    private Collection<QuizEntity> quizEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="school_id")
    private SchoolEntity school;
}
