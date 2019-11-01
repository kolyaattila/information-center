package com.videoservice.converter;

import com.videoservice.entity.Video;
import com.videoservice.model.VideoDto;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoConverter {

    VideoDto toDto(Video video);

    Video toEntity(VideoRequest videoRequest);

    Video toEntity(VideoDto videoDto);

    VideoResponse toResponse(Video video);
}
