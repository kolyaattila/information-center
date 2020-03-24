package com.information.center.questionservice.converter;

import com.information.center.questionservice.entity.Question;
import com.information.center.questionservice.model.QuestionResponseValidated;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.model.response.QuestionResponsePage;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",uses = AnswerConverter.class)
public interface QuestionConverter {

    Question toEntity(QuestionRequest questionRequest);

    @Mapping(source = "topicExternalId",target = "topicExternalId")
    QuestionResponse toResponse(Question question);

    Question toEntity(QuestionResponse questionResponse, long id);

    QuestionRequest toRequest(Question question);

    QuestionResponse toResponse(QuestionRequest questionRequest);

    QuestionResponseValidated toResponse(QuestionRequestValidation questionRequestValidation);
}
