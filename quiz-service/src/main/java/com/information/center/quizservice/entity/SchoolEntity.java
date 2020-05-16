package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "School")
public class SchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false, length = 50)
    private String externalId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "number_questions", nullable = false)
    private int numberOfQuestions;

    @Column(name = "created")
    private Date created = new Date();
}
