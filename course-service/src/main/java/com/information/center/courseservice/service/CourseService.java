package com.information.center.courseservice.service;

import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.request.CourseRequest;
import com.information.center.courseservice.model.request.FilterCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    void saveCourse(CourseRequest courseRequest);

    void updateCourse(CourseRequest courseRequest);

    void deleteCourse(String externalId);

    Page<CourseDto> filterCourse(FilterCourseRequest filterCourseRequest);

    CourseDetailsDto getCourseById(String externalId);

    List<CourseDto> getAllActiveCourses();

    List<CourseDto> getAllCourses();
}
