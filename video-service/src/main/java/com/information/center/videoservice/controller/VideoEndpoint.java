package com.information.center.videoservice.controller;

import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/video")
public interface VideoEndpoint {

    @GetMapping("/{externalId}/details")
    VideoResponse findById(@PathVariable("externalId") String externalId);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    VideoResponse create(@RequestBody VideoRequest videoRequest) throws IOException;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void update(@RequestBody VideoDto videoDto) throws IOException;

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId);

    @GetMapping("/{externalId}")
    public ResponseEntity<UrlResource> getFullVideo(@PathVariable("externalId") String externalId) throws MalformedURLException;

    @GetMapping("/{topicId}/byChapter")
    public List<VideoResponse> findAllByTopicId(@PathVariable("topicId") String topicId);
}
