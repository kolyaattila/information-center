package com.information.center.topicservice.converter;
import com.information.center.topicservice.entity.Topic;
import com.information.center.topicservice.model.TopicDto;
import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicConverter {

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicRequest topicRequest);

    TopicResponse toResponse(Topic topic);

    Topic toEntity(TopicResponse topicResponse);



}
