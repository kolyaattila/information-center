package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.request.ReviewRequest;
import com.information.center.courseservice.service.ReviewService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RequiredArgsConstructor
@Component
public class ReviewController implements ReviewEndpoint {

    private final ReviewService reviewService;

    @Override
    public void reviewCourse(@Valid @RequestBody ReviewRequest reviewRequest) {
        try {
            reviewService.reviewCourse(reviewRequest);
        } catch (ServiceExceptions.NotFoundException | ServiceExceptions.InsertFailedException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }
}
