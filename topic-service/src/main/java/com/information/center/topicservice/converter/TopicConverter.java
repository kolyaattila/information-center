package com.information.center.topicservice.converter;

import com.information.center.topicservice.entity.TopicEntity;
import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicConverter {

  TopicEntity toEntity(TopicRequest topicRequest);

  TopicResponse toResponse(TopicEntity topic);

  TopicEntity toEntity(TopicRequest topicRequest, long id);

}
