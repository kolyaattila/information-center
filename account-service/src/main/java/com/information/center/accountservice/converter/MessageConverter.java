package com.information.center.accountservice.converter;

import com.information.center.accountservice.entity.MessageEntity;
import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.model.MessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageConverter {

    MessageEntity toEntity(MessageRequest messageRequest);

    MessageResponse toResponse(MessageEntity messageEntity);

}
