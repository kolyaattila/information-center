package com.information.center.quizservice.converter;

import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.model.AnswerDto;
import com.information.center.quizservice.model.request.AnswerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AnswerConverter {

    AnswerEntity toEntity(AnswerRequest answerRequest);

    @Mapping(target = "questionExternalId", expression = "java(entity.getQuestion() != null ?  entity.getQuestion().getExternalId() : null)")
    AnswerDto toDto(AnswerEntity entity);

    @Mappings({
            @Mapping(target = "correct", ignore = true),
            @Mapping(target = "questionExternalId", expression = "java(entity.getQuestion() != null ?  entity.getQuestion().getExternalId() : null)")
    })
    AnswerDto toDtoWithoutCorrect(AnswerEntity entity);
}
