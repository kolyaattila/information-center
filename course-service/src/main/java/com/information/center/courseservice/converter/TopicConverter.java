package com.information.center.courseservice.converter;

import com.information.center.courseservice.entity.TopicEntity;
import com.information.center.courseservice.model.request.TopicRequest;
import com.information.center.courseservice.model.response.TopicResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicConverter {

  TopicEntity toEntity(TopicRequest topicRequest);

  TopicResponse toResponse(TopicEntity topic);

  TopicEntity toEntity(TopicRequest topicRequest, long id);

}
