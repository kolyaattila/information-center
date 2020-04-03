package com.information.center.videoservice.controller;

import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.service.VideoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController implements VideoEndpoint {

    private final VideoServiceImpl videoService;

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
    public ResponseEntity<UrlResource> getFullVideo(@PathVariable("externalId") String externalId) throws MalformedURLException {
        return videoService.getFullVideo(externalId);

    }

    @GetMapping("/{topicId}/byChapter")
    public List<VideoResponse> findAllByTopicId(@PathVariable("topicId") String topicId) {
        return videoService.findAllByTopicId(topicId);
    }

}
