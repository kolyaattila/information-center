package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.request.CourseRequest;
import com.information.center.courseservice.model.request.FilterCourseRequest;
import com.information.center.courseservice.service.CourseService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseController implements CourseEndpoint {

    private final CourseService courseService;

    @Override
    public void createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        try {
            courseService.saveCourse(courseRequest);
        } catch (ServiceExceptions.InsertFailedException e) {
            e.printStackTrace();
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public void updateCourse(@Valid @RequestBody CourseRequest courseRequest) {
        try {
            courseService.updateCourse(courseRequest);
        } catch (ServiceExceptions.InsertFailedException | ServiceExceptions.NotFoundException e) {
            e.printStackTrace();
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public void deleteCourse(String externalId) {
        try {
            courseService.deleteCourse(externalId);
        } catch (ServiceExceptions.NotFoundException e) {
            e.printStackTrace();
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public Page<CourseDto> filterCourse(@RequestBody FilterCourseRequest filterCourseRequest) {
        return courseService.filterCourse(filterCourseRequest);
    }

    @Override
    public CourseDetailsDto getCourse(@PathVariable String externalId) {
        try {
            return courseService.getCourseById(externalId);
        } catch (ServiceExceptions.NotFoundException e) {
            e.printStackTrace();
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public List<CourseDto> getAllActiveCourses() {
        return courseService.getAllActiveCourses();
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses();
    }
}
