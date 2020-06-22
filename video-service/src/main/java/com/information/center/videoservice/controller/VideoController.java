package com.information.center.videoservice.controller;

import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.service.VideoService;
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

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class VideoController implements VideoEndpoint {

    private final VideoService videoService;

    @Override
    public VideoResponse findById(@PathVariable("externalId") String externalId) {
        return videoService.findByExternalId(externalId);
    }

    @Override
    public VideoResponse create(@ModelAttribute VideoRequest videoRequest) throws IOException {
        return videoService.create(videoRequest);
    }

    @Override
    public void update(@RequestBody VideoDto videoDto) throws IOException {
        videoService.update(videoDto);
    }

    @Override
    public void delete(@PathVariable("externalId") String externalId) throws Exception {
        videoService.delete(externalId);
    }

    @Override
    public ResponseEntity<UrlResource> getFullVideo(@PathVariable("externalId") String externalId) throws MalformedURLException {
        return videoService.getFullVideo(externalId);

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
