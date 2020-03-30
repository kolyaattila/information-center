package com.information.center.questionservice.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Question")
public class QuestionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
  @Column(name = "difficulty", nullable = false)
  private QuestionDifficulty questionDifficulty;

  @Column(name = "topic_id", nullable = false)
  private String topicExternalId;

  private Date created;
}
