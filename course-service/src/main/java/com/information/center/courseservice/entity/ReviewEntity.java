package com.information.center.courseservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "created", nullable = false)
    private Date created= new Date();

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "account_id")
    private String AccountId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false, updatable = false)
    private CourseEntity course;

}
