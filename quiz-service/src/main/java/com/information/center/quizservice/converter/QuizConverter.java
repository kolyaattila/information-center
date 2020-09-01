package com.information.center.quizservice.converter;

import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.entity.QuizEntity;
import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class QuizConverter {

    public abstract QuizEntity toEntity(QuizRequest quizRequest);

    @Mapping(target = "schoolExternalId", expression = "java(entity.getSchool() != null ? entity.getSchool().getExternalId() : null)" )
    public abstract QuizDto toDto(QuizEntity entity);

    @Mapping(target = "questions", ignore = true)
    public abstract QuizStartDto toQuizStartDto(QuizEntity entity);


    public QuizDto toDtoWithQuestions(QuizEntity entity) {
        QuizDto quizDto = toDto(entity);

        quizDto.setQuestionIds(entity.getQuestions()
                .stream()
                .map(QuestionEntity::getExternalId)
                .collect(Collectors.toList()));
        return quizDto;
    }
}
