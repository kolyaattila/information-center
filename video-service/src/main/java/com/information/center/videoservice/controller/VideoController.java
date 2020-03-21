package com.information.center.videoservice.controller;

import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController implements VideoEndpoint {

  private final VideoService videoService;

  @Override
  public VideoResponse findById(@PathVariable("externalId") String externalId) {
    return videoService.findByExternalId(externalId);
  }

  @Override
  public VideoResponse create(@RequestBody VideoRequest videoRequest) {
    return videoService.create(videoRequest);
  }

  @Override
  public void update(@RequestBody VideoDto videoDto) {
    videoService.update(videoDto);
  }

  @Override
  public void delete(@PathVariable("externalId") String externalId) {
    videoService.delete(externalId);
  }
}
