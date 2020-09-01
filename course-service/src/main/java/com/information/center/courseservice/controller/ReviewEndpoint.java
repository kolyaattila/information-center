package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.request.ReviewRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/course-service/review")
public interface ReviewEndpoint {

    @PostMapping
    void reviewCourse(@RequestBody @Valid ReviewRequest reviewRequest);

}
