package com.information.center.courseservice.controller;


import com.information.center.courseservice.model.VideoDto;
import com.information.center.courseservice.model.VideoRequest;
import com.information.center.courseservice.model.VideoResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/course-service/video")
public interface VideoEndpoint {

    @GetMapping("/{externalId}/details")
    VideoResponse findById(@PathVariable("externalId") String externalId);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    VideoResponse create(@ModelAttribute VideoRequest videoRequest) throws IOException;

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void update(@RequestBody VideoDto videoDto) throws IOException;

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId) throws Exception;

    @GetMapping("/{topicId}/byChapter")
    List<VideoResponse> findAllByTopicId(@PathVariable("topicId") String topicId);

    @GetMapping("/byCourse/{courseId}")
    List<VideoResponse> findAllByCourseId(@PathVariable("courseId") String courseId);
}
