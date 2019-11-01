package com.videoservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoResponse {

    private String externalId;

    private String path;

    private String title;

    private String description;

    private String chapter;
}
