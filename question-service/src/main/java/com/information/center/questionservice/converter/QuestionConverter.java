package com.information.center.questionservice.converter;

import com.information.center.questionservice.entity.Question;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.QuestionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionConverter {

    Question toEntity(QuestionRequest questionRequest);

    QuestionResponse toResponse(Question question);

    Question toEntity(QuestionResponse questionResponse, long id);

    QuestionRequest toRequest(Question question);

    QuestionResponse toResponse(QuestionRequest questionRequest);
}
