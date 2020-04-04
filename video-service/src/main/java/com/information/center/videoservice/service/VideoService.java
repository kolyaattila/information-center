package com.information.center.videoservice.service;

import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Service
public interface VideoService {

    VideoResponse findByExternalId(String externalId);

    VideoResponse create(VideoRequest videoRequest) throws IOException;

    void update(VideoDto videoDto) throws IOException;

    void delete(String externalId);

    VideoEntity findById(String externalId);

    List<VideoResponse> findAllByTopicId(String topicId);

    ResponseEntity<UrlResource> getFullVideo(String externalId) throws MalformedURLException;
}
