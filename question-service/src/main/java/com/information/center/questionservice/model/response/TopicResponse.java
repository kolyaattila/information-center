package com.information.center.questionservice.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class TopicResponse {

  @Tolerate
  public TopicResponse() {
  }

  private String name;

  private String externalId;

}
