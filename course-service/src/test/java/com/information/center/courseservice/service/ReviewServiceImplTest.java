package com.information.center.courseservice.service;

import com.information.center.courseservice.converter.ReviewConverter;
import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.entity.ReviewEntity;
import com.information.center.courseservice.model.RatingDto;
import com.information.center.courseservice.model.request.ReviewRequest;
import com.information.center.courseservice.repository.CourseRepository;
import com.information.center.courseservice.repository.ReviewRepository;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {
	private static final String AUTHOR = "author";
	private static final String DESCRIPTION_ENTITY = "descriptionEntity";
	private static final String NAME_ENTITY = "nameEntity";
	private static final String ACCOUNT_EXT_ID = "accountExtId";
	private static final String COURSE_EXT_ID = "courseExtId";
	private static final String MESSAGE = "message";
	private static final String EXT_ID = "extId";

	@Mock
	private ReviewRepository reviewRepository;
	@Mock
	private CourseRepository courseRepository;
	@InjectMocks
	private ReviewServiceImpl reviewService;

	private ReviewRequest reviewRequest;
	private CourseEntity courseEntity;
	private ReviewEntity reviewEntity;
	private final ArgumentCaptor<ReviewEntity> captor = ArgumentCaptor.forClass(ReviewEntity.class);

	@Before
	public void setUp() {
		ReviewConverter reviewConverter = Mappers.getMapper(ReviewConverter.class);
		reviewService.setReviewConverter(reviewConverter);

		reviewRequest = ReviewRequest.builder()
				.accountExternalId(ACCOUNT_EXT_ID)
				.courseExternalId(COURSE_EXT_ID)
				.message(MESSAGE)
				.rating(3)
				.build();

		courseEntity = new CourseEntity();
		courseEntity.setAuthor(AUTHOR);
		courseEntity.setCreated(new Date());
		courseEntity.setDescription(DESCRIPTION_ENTITY);
		courseEntity.setExternalId(EXT_ID);
		courseEntity.setName(NAME_ENTITY);
		courseEntity.setPrice(23);

		reviewEntity = new ReviewEntity();
		reviewEntity.setRating(1);
		reviewEntity.setExternalId(EXT_ID);
		reviewEntity.setAccountExternalId(ACCOUNT_EXT_ID);
		reviewEntity.setCreated(new Date());
		reviewEntity.setMessage(MESSAGE);
		reviewEntity.setCourse(courseEntity);
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void reviewCourseWhenCourseNotFound_expectException() {
		when(courseRepository.findByExternalId(COURSE_EXT_ID)).thenReturn(Optional.empty());

		reviewService.reviewCourse(reviewRequest);
	}

	@Test
	public void reviewCourseWhenIsFirstReview_expectToCreateReview() {
		when(courseRepository.findByExternalId(COURSE_EXT_ID)).thenReturn(Optional.of(courseEntity));
		when(reviewRepository.findByCourseExternalIdAndAccountExternalId(reviewRequest.getCourseExternalId(), reviewRequest.getAccountExternalId()))
				.thenReturn(Optional.empty());
		when(reviewRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		reviewService.reviewCourse(reviewRequest);

		verify(reviewRepository).save(captor.capture());
		ReviewEntity value = captor.getValue();
		assertThat(value.getCourse(), is(courseEntity));
		assertThat(value.getRating(), is(reviewRequest.getRating()));
	}

	@Test
	public void reviewCourseWhenHasAlreadyReview_expectToUpdateReview() {
		when(courseRepository.findByExternalId(COURSE_EXT_ID)).thenReturn(Optional.of(courseEntity));
		when(reviewRepository.findByCourseExternalIdAndAccountExternalId(reviewRequest.getCourseExternalId(), reviewRequest.getAccountExternalId()))
				.thenReturn(Optional.of(reviewEntity));

		reviewService.reviewCourse(reviewRequest);

		verify(reviewRepository).save(captor.capture());
		ReviewEntity value = captor.getValue();
		assertThat(value.getCourse(), is(courseEntity));
		assertThat(value.getRating(), is(reviewRequest.getRating()));
	}

	@Test
	public void getRating() {
		courseEntity.setReviews(buildReviews());
		RatingDto rating = reviewService.getRating(courseEntity);

		assertThat(rating.getAverage(), is(3.0F));
		assertThat(rating.getNumberOfReviews(), is(5));
		assertThat(rating.getFiveStarsReviews(), is(1));
		assertThat(rating.getFourStarsReviews(), is(1));
		assertThat(rating.getThreeStarsReviews(), is(1));
		assertThat(rating.getTwoStarsReviews(), is(1));
		assertThat(rating.getOneStarsReviews(), is(1));
	}

	private List<ReviewEntity> buildReviews() {
		List<ReviewEntity> reviews = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			ReviewEntity reviewEntity = new ReviewEntity();
			reviewEntity.setRating(i + 1);
			reviews.add(reviewEntity);
		}
		return reviews;
	}
}
