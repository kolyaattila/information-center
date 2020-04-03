package com.information.center.videoservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
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
}
