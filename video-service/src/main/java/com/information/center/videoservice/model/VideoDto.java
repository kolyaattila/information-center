package com.information.center.videoservice.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

    private MultipartFile file;
    private String externalId;
    private String path;
    private String title;
    private String description;
    private String courseExternalId;
    private String topicExternalId;
    private String videoDuration;
}
