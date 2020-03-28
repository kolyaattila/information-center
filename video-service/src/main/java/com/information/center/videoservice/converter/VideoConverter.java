package com.information.center.videoservice.converter;

import com.information.center.videoservice.entity.Video;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoConverter {

    VideoDto toDto(Video video);

    Video toEntity(VideoRequest videoRequest);

    Video toEntity(VideoDto videoDto);

    VideoResponse toResponse(Video video);
}
