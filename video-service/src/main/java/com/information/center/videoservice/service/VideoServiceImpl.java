package com.information.center.videoservice.service;

import com.information.center.videoservice.converter.VideoConverter;
import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.repository.VideoRepository;
import exception.ServiceExceptions.InconsistentDataException;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoServiceImpl implements VideoService {

  private final VideoRepository videoRepository;
  private final VideoConverter videoConverter;

  @Override
  public VideoResponse findByExternalId(String externalId) {
    return videoConverter.toResponse(findById(externalId));
  }

  @Override
  public VideoResponse create(VideoRequest videoRequest) {
    var video = videoConverter.toEntity(videoRequest);
    video.setUid(UUID.randomUUID().toString());
    return videoConverter.toResponse(videoRepository.save(video));
  }

  @Override
  public void update(VideoDto videoDto) {

    var video = findById(videoDto.getUid());
    var videoPersistent = videoConverter.toEntity(videoDto);
    videoPersistent.setId(video.getId());
    videoRepository.save(videoPersistent);
  }

  @Override
  public void delete(String externalId) {
    var video = findById(externalId);
    videoRepository.delete(video);
  }

  @Override
  public VideoEntity findById(String externalId) {

    return videoRepository.findByUid(externalId)
        .orElseThrow(throwNotFoundItem("video", externalId));
  }

  private Supplier<InconsistentDataException> throwNotFoundItem(String item, String itemId) {
    return () -> new InconsistentDataException("Cannot find " + item + " by id " + itemId);
  }
}

