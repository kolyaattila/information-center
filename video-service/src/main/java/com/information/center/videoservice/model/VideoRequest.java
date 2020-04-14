package com.information.center.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class VideoRequest {

    @Tolerate
    public VideoRequest() {
    }

    private MultipartFile file;

    private String path;

    private String title;

    private String description;

    private String chapter;

    private String topicId;
}
