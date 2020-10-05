package com.information.center.courseservice.converter;

import com.information.center.courseservice.entity.VideoEntity;
import com.information.center.courseservice.model.VideoDto;
import com.information.center.courseservice.model.VideoRequest;
import com.information.center.courseservice.model.VideoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoConverter {

    VideoDto toDto(VideoEntity video);

    VideoEntity toEntity(VideoRequest videoRequest);

    VideoEntity toEntity(VideoDto videoDto);

    VideoResponse toResponse(VideoEntity video);
}
