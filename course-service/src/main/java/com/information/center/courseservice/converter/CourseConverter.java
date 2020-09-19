package com.information.center.courseservice.converter;

import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.request.CourseRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseConverter {

    CourseEntity toEntity(CourseRequest courseRequest);

    CourseDto toDto(CourseEntity entity);

    CourseDetailsDto toDetailsDto(CourseEntity entity);

}
