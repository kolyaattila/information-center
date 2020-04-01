package com.information.center.questionservice.converter;

import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.QuestionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionConverter {

  QuestionEntity toEntity(QuestionRequest questionRequest);

  QuestionResponse toResponse(QuestionEntity question);

  QuestionEntity toEntity(QuestionRequest questionRequest, long id);

  QuestionRequest toRequest(QuestionEntity question);

  QuestionResponse toResponse(QuestionRequest questionRequest);
}
