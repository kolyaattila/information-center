package com.information.center.courseservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {

    private String name;
    private String externalId;
}
