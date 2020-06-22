package com.information.center.courseservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "created", nullable = false)
    private Date created = new Date();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "enable", nullable = false)
    private boolean enable;

    @Column(name = "image")
    private String image;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "number_videos", nullable = false)
    private int numberVideos;

    @Column(name = "number_quizzes", nullable = false)
    private int numberQuizzes;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<TopicEntity> topics = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<ReviewEntity> reviews = new ArrayList<>();
}
