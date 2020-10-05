package com.information.center.courseservice.controller;


import com.information.center.courseservice.model.VideoDto;
import com.information.center.courseservice.model.VideoRequest;
import com.information.center.courseservice.model.VideoResponse;
import com.information.center.courseservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class VideoController implements VideoEndpoint {

    private final VideoService videoService;

    @Override
    public VideoResponse findById(@PathVariable("externalId") String externalId) {
        return videoService.findByExternalId(externalId);
    }

    @Override
    public VideoResponse create(VideoRequest videoRequest) throws IOException {
        return videoService.create(videoRequest);
    }

    @Override
    public void update(VideoDto videoDto) throws IOException {
        videoService.update(videoDto);
    }

    @Override
    public void delete(@PathVariable("externalId") String externalId) throws Exception {
        videoService.delete(externalId);
    }

    @Override
    public List<VideoResponse> findAllByTopicId(@PathVariable("topicId") String topicId) {
        return videoService.findAllByTopicId(topicId);
    }

    @Override
    public List<VideoResponse> findAllByCourseId(@PathVariable("courseId") String courseId) {
        return videoService.findAllByCourseId(courseId);
    }

}
