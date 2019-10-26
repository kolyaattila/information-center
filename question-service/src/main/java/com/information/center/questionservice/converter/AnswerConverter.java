package com.information.center.questionservice.converter;

import com.information.center.questionservice.entity.Answer;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerConverter {

     Answer toEntity(AnswerRequest answerRequest);

     AnswerResponse toResponse(Answer answer);

     Answer toEntity(AnswerResponse answerResponse,long id);
}
