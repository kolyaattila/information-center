package com.information.center.topicservice.converter;
import com.information.center.topicservice.entity.Topic;
import com.information.center.topicservice.model.TopicDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicConverter {

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicDto topicDto);

}
