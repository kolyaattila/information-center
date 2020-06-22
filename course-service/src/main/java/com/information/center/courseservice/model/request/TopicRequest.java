package com.information.center.courseservice.model.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest {

    @Length(min = 3, max = 255)
    private String name;
    private String externalId;
    private String courseExternalId;
}
