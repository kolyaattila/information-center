package com.information.center.videoservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Video")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id", unique = true, nullable = false, length = 50)
    private String externalId;

    @Column(name = "path", nullable = false, length = 255, unique = true)
    private String path;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "course_id", nullable = false, length = 50)
    private String courseExternalId;

    @Column(name = "topic_id", nullable = false, length = 50)
    private String topicExternalId;

    @Column(name = "video_duration", nullable = false, length = 10)
    private String videoDuration;

    @Column(name = "created")
    private Date created = new Date();
}
