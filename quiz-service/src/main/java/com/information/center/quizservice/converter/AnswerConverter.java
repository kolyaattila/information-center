package com.information.center.quizservice.converter;

import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.model.response.AnswerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerConverter {

  AnswerEntity toEntity(AnswerRequest answerRequest);

  AnswerResponse toResponse(AnswerEntity answer);

  AnswerEntity toEntity(AnswerRequest answerRequest, long id);
}
