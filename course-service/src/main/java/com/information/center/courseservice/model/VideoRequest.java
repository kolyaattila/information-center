package com.information.center.courseservice.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequest {

    private MultipartFile file;
    private String title;
    private String description;
    private String topicExternalId;
    private String videoDuration;
}
