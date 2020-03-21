package com.information.center.videoservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Data
@Builder
public class VideoDto {

    @Tolerate
    public VideoDto() {
    }

    private String uid;

    private String path;

    private String title;

    private String description;

    private String chapterId;
}
