package com.information.center.courseservice.service;

import com.information.center.courseservice.entity.VideoEntity;
import com.information.center.courseservice.model.VideoDto;
import com.information.center.courseservice.model.VideoRequest;
import com.information.center.courseservice.model.VideoResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface VideoService {

    VideoResponse findByExternalId(String externalId);

    VideoResponse create(VideoRequest videoRequest) throws IOException;

    void update(VideoDto videoDto) throws IOException;

    void delete(String externalId) throws Exception;

    VideoEntity findById(String externalId);

    List<VideoResponse> findAllByTopicId(String topicId);

    List<VideoResponse> findAllByCourseId(String courseId);
}
