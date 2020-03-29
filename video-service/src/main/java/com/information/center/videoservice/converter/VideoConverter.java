package com.information.center.videoservice.converter;

import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoConverter {

  VideoDto toDto(VideoEntity video);

  VideoEntity toEntity(VideoRequest videoRequest);

  VideoEntity toEntity(VideoDto videoDto);

  VideoResponse toResponse(VideoEntity video);
}
