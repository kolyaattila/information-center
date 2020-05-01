package com.information.center.quizservice.converter;

import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.QuestionResponseValidated;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequestValidation;
import com.information.center.quizservice.model.response.QuestionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionConverter {

  QuestionEntity toEntity(QuestionRequest questionRequest);

  @Mapping(source = "topicExternalId",target = "topicExternalId")
  QuestionResponse toResponse(QuestionEntity question);

  QuestionEntity toEntity(QuestionResponse questionResponse, long id);

  QuestionEntity toEntity(QuestionRequest questionResponse, long id);

  QuestionRequest toRequest(QuestionEntity question);

  QuestionResponse toResponse(QuestionRequest questionRequest);

  QuestionResponseValidated toResponse(QuestionRequestValidation questionRequestValidation);
}