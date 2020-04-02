package com.information.center.questionservice.converter;

import com.information.center.questionservice.entity.AnswerEntity;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerConverter {

  AnswerEntity toEntity(AnswerRequest answerRequest);

  AnswerResponse toResponse(AnswerEntity answer);

  AnswerEntity toEntity(AnswerRequest answerRequest, long id);

  AnswerEntity toEntity(AnswerResponse answerRequest, long id);
}
