package com.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private String externalId;

    private String path;

    private String title;

    private String description;

    private String chapter;
}
