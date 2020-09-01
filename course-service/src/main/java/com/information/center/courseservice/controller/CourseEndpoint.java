package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.request.CourseRequest;
import com.information.center.courseservice.model.request.FilterCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course-service/")
public interface CourseEndpoint {

    @PostMapping
    void createCourse(@RequestBody @Valid CourseRequest courseRequest);


    @PutMapping
    void updateCourse(@RequestBody @Valid CourseRequest courseRequest);


    @DeleteMapping("/{externalId}")
    void deleteCourse(@PathVariable String externalId);

    @PostMapping("/filter")
    Page<CourseDto> filterCourse(@RequestBody FilterCourseRequest filterCourseRequest);

    @GetMapping("/course/active/{externalId}")
    CourseDetailsDto getCourse(@PathVariable String externalId);

    @GetMapping("/course/active")
    List<CourseDto> getAllActiveCourses();

    @GetMapping("/course")
    List<CourseDto> getAllCourses();

    @GetMapping("/course/best")
    List<CourseDto> getBestCourses();

}
