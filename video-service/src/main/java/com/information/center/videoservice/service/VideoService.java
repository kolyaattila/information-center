package com.information.center.videoservice.service;

import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.springframework.stereotype.Service;

@Service
public interface VideoService {

  VideoResponse findByExternalId(String externalId);

  VideoResponse create(VideoRequest videoRequest);

  void update(VideoDto videoDto);

  void delete(String externalId);

  VideoEntity findById(String externalId);
}
