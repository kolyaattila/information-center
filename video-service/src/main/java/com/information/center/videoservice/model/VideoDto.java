package com.information.center.videoservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoDto {

    private String externalId;

    private String path;

    private String title;

    private String description;

    private String chapter;
}
