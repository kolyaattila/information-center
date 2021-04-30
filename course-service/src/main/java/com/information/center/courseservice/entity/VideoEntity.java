package com.information.center.courseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_generator")
    @SequenceGenerator(name = "video_generator", sequenceName = "S_VIDEO_0", allocationSize = 1)
    private long id;

    @Column(name = "external_id", unique = true, nullable = false, length = 50)
    private String externalId;

    @Column(name = "path", nullable = false, length = 255, unique = true)
    private String path;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private TopicEntity topic;

    @Column(name = "video_duration", nullable = false, length = 10)
    private String videoDuration;

    @Column(name = "created")
    private Date created = new Date();
}
