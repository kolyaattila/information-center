package com.videoservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Video {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String externalId;

  private String path;

  private String title;

  private String description;

  private String chapter;

  private long topicId;
}
