package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Quiz")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false, length = 50)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuizType type;

    @Column(name = "duration")
    private int duration;

    @Column(name = "created")
    private Date created = new Date();

    @Column(name = "chapter_id", length = 50)
    private String chapterExternalId;

    @Column(name = "course_id", length = 50)
    private String courseExternalId;

    @Column(name = "enable")
    private Boolean enable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="school_id")
    private SchoolEntity school;

    @ManyToMany
    @JoinTable(
            name = "question2quiz",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    Collection<QuestionEntity> questions;


    @Override
    public String toString() {
        return "QuizEntity{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", type=" + type +
                '}';
    }
}
