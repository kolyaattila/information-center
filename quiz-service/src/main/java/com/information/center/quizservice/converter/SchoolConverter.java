package com.information.center.quizservice.converter;

import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolConverter {

    SchoolEntity toEntity(SchoolRequest schoolRequest);

    SchoolDto toDto(SchoolEntity entity);
}
