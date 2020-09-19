package com.information.center.courseservice.service;

import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.model.RatingDto;
import com.information.center.courseservice.model.request.ReviewRequest;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void reviewCourse(ReviewRequest reviewRequest);

    RatingDto getRating(CourseEntity entity);
}
