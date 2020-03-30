package com.information.center.questionservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class TopicDto {

  @Tolerate
  public TopicDto() {
  }

  private String externalId;

  private String name;
}
