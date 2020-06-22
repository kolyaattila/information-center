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
    VideoResponse create(@ModelAttribute VideoRequest videoRequest) throws IOException;

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void update(@RequestBody VideoDto videoDto) throws IOException;

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId) throws Exception;

    @GetMapping("/{externalId}")
    ResponseEntity<UrlResource> getFullVideo(@PathVariable("externalId") String externalId) throws MalformedURLException;

    @GetMapping("/{topicId}/byChapter")
    List<VideoResponse> findAllByTopicId(@PathVariable("topicId") String topicId);

    @GetMapping("/byCourse/{courseId}")
    List<VideoResponse> findAllByCourseId(@PathVariable("courseId") String courseId);
}
