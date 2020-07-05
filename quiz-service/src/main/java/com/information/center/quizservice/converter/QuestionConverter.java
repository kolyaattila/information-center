package com.information.center.quizservice.converter;

import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.QuestionResponseValidated;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequestValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class QuestionConverter {

    private AnswerConverter answerConverter;

    @Mappings({
            @Mapping(target = "answers", ignore = true),
            @Mapping(target = "quizEntities", ignore = true)
    })
    public abstract QuestionEntity toEntity(QuestionRequest questionRequest);

    public abstract QuestionResponseValidated toResponse(QuestionRequestValidation questionRequestValidation);

    @Mappings({
            @Mapping(target = "schoolExternalId", expression = "java(entity.getSchool() != null ?  entity.getSchool().getExternalId() : null)"),
            @Mapping(target = "status", defaultValue = "false")
    })
    public abstract QuestionDto toDto(QuestionEntity entity);


    public QuestionDto toDtoWithAnswers(QuestionEntity entity) {
        QuestionDto dto = this.toDto(entity);
        dto.setAnswers(entity.getAnswers().stream().map(answerConverter::toDto).collect(Collectors.toList()));
        return dto;
    }

    public QuestionDto toDtoWithAnswersWithoutCorrectValue(QuestionEntity entity) {
        QuestionDto dto = this.toDto(entity);
        dto.setAnswers(entity.getAnswers().stream().map(answerConverter::toDtoWithoutCorrect).collect(Collectors.toList()));
        return dto;
    }

    @Autowired
    public void setAnswerConverter(AnswerConverter answerConverter) {
        this.answerConverter = answerConverter;
    }
}
