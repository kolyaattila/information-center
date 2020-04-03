package com.information.center.videoservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Video")
public class VideoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "externalId", unique = true, nullable = false)
  private String externalId;

  @Column(name = "path", nullable = false)
  private String path;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "chapter_id", nullable = false)
  private String chapter;

  @Column(name = "topic_id", nullable = false)
  private long topicId;
}
