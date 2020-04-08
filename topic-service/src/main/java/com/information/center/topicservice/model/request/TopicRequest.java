package com.information.center.topicservice.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
public class TopicRequest {

    @Tolerate
    public TopicRequest() {
    }

    @Length(min = 3, max = 255)
    private String name;
    @Length(min = 3, max = 255)
    private String externalId;
}
