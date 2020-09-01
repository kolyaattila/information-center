package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.request.ReviewRequest;
import com.information.center.courseservice.service.ReviewService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {

	@Mock
	private ReviewService reviewService;
	@InjectMocks
	private ReviewController reviewController;

	private ReviewRequest reviewRequest;

	@Before
	public void setUp() {
		reviewRequest = ReviewRequest.builder()
				.accountExternalId("accountExtId")
				.courseExternalId("courseExtId")
				.message("message")
				.rating(23)
				.build();

	}

	@Test
	public void reviewCourse_expectToCallService() {
		reviewController.reviewCourse(reviewRequest);

		verify(reviewService).reviewCourse(reviewRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void reviewCourse_expectException() {
		doThrow(ServiceExceptions.NotFoundException.class).when(reviewService).reviewCourse(reviewRequest);

		reviewController.reviewCourse(reviewRequest);
	}
}
