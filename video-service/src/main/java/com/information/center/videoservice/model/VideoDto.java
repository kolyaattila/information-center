package com.information.center.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class VideoDto {

    @Tolerate
    public VideoDto() {
    }

    private MultipartFile file;

    private String externalId;

    private String path;

    private String title;

    private String description;

    private String chapter;

    private String topicId;

    private String videoDuration;
}
