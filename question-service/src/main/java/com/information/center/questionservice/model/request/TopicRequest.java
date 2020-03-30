package com.information.center.questionservice.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class TopicRequest {

  @Tolerate
  public TopicRequest() {
  }

  private String name;

}
