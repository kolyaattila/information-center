package com.videoservice.controller;

import com.videoservice.model.VideoDto;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import com.videoservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{externalId}/details")
    public VideoResponse findById(@PathVariable("externalId") String externalId) {
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
    public void delete(@PathVariable("externalId") String externalId) {
        videoService.delete(externalId);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<UrlResource> getFullVideo(@PathVariable("externalId")String externalId) throws MalformedURLException {
       return videoService.getFullVideo(externalId);

    }
    @GetMapping("/{topicId}/byChapter")
    public List<VideoResponse> findAllByTopicId(@PathVariable("topicId") String topicId) {
        return videoService.findAllByTopicId(topicId);
    }

}
