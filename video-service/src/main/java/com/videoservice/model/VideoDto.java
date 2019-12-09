package com.videoservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class VideoDto {

    private MultipartFile file;

    private String externalId;

    private String path;

    private String title;

    private String description;

    private String chapter;
}
