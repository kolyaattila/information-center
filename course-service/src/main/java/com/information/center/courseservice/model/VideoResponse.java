package com.information.center.courseservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private String externalId;
    private String path;
    private String title;
    private String description;
    private String courseExternalId;
    private String topicExternalId;
    private String videoDuration;
}
