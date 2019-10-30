package com.information.center.questionservice.converter;

import com.information.center.questionservice.entity.Topic;
import com.information.center.questionservice.model.TopicDto;
import com.information.center.questionservice.model.request.TopicRequest;
import com.information.center.questionservice.model.response.TopicResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicConverter {

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicRequest topicRequest);

    TopicResponse toResponse(Topic topic);

    Topic toEntity(TopicResponse topicResponse,long id);
}
