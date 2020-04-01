package com.videoservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class VideoRequest {

    private MultipartFile file;

    private String path;

    private String title;

    private String description;

    private String chapter;
}
