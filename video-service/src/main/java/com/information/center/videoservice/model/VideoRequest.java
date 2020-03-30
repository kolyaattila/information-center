package com.information.center.videoservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

@Data
@Builder
public class VideoRequest {

    @Tolerate
    public VideoRequest() {
    }

    private String path;

    private String title;

    private String description;

    private String chapter;
}
