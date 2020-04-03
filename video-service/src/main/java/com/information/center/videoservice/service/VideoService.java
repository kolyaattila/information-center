package com.information.center.videoservice.service;

import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface VideoService {

  VideoResponse findByExternalId(String externalId);

  VideoResponse create(VideoRequest videoRequest) throws IOException;

  void update(VideoDto videoDto) throws IOException;

  void delete(String externalId);

  VideoEntity findById(String externalId);
}
