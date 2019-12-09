package com.videoservice.controller;

import com.videoservice.model.VideoDto;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import com.videoservice.service.VideoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{externalId}")
   public VideoResponse findById(@PathVariable("externalId") String externalId){
        return videoService.findByExternalId(externalId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VideoResponse create(VideoRequest videoRequest) throws IOException {
       return videoService.create(videoRequest);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void update(VideoDto videoDto) throws IOException {
        videoService.update(videoDto);
    }

    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId){
        videoService.delete(externalId);
    }


}
