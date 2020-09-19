package com.information.center.courseservice.converter;

import com.information.center.courseservice.entity.ReviewEntity;
import com.information.center.courseservice.model.request.ReviewRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewConverter {

    ReviewEntity toEntity(ReviewRequest reviewRequest);

}
