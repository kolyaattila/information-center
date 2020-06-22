package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.request.CourseRequest;
import com.information.center.courseservice.model.request.FilterCourseRequest;
import com.information.center.courseservice.service.CourseService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseControllerTest {

	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	private static final String AUTHOR = "author";
	private static final String EXT_ID = "extId";
	private static final String DELETE_EXTERNAL_ID = "deleteExternalId";
	@Mock
	private CourseService courseService;

	@InjectMocks
	private CourseController courseController;

	private CourseRequest courseRequest;
	private FilterCourseRequest filterCourseRequest;
	private CourseDto courseDto;
	private CourseDetailsDto courseDetailsDto;

	@Before
	public void setUp() {
		courseRequest = CourseRequest.builder()
				.description(DESCRIPTION)
				.enable(true)
				.name(NAME)
				.price(2)
				.build();

		filterCourseRequest = FilterCourseRequest.builder()
				.enable(false)
				.externalId(EXT_ID)
				.name(NAME)
				.pageNumber(33)
				.price(34)
				.pageSize(12)
				.build();

		courseDto = CourseDto.builder()
				.author(AUTHOR)
				.created(new Date())
				.description(DESCRIPTION)
				.enable(true)
				.externalId(EXT_ID)
				.name(NAME)
				.build();
		courseDetailsDto = CourseDetailsDto.builder()
				.author(AUTHOR)
				.created(new Date())
				.description(DESCRIPTION)
				.enable(true)
				.externalId(EXT_ID)
				.numberDocuments(23)
				.numberQuizzes(3)
				.numberStudents(42)
				.numberTopics(54)
				.numberVideos(4)
				.name(NAME)
				.build();
	}

	@Test
	public void createCourse_expectToCallService() {
		courseController.createCourse(courseRequest);

		verify(courseService).saveCourse(courseRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void createCourse_expectException() {
		doThrow(ServiceExceptions.InsertFailedException.class).when(courseService).saveCourse(courseRequest);

		courseController.createCourse(courseRequest);

		verify(courseService).saveCourse(courseRequest);
	}

	@Test
	public void updateCourse_expectToCallService() {
		courseController.updateCourse(courseRequest);

		verify(courseService).updateCourse(courseRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void updateCourse_expectException() {
		doThrow(ServiceExceptions.NotFoundException.class).when(courseService).updateCourse(courseRequest);

		courseController.updateCourse(courseRequest);
	}

	@Test
	public void deleteCourse_expectToCallService() {
		courseController.deleteCourse(DELETE_EXTERNAL_ID);

		verify(courseService).deleteCourse(DELETE_EXTERNAL_ID);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void deleteCourse_expectException() {
		doThrow(ServiceExceptions.NotFoundException.class).when(courseService).deleteCourse(DELETE_EXTERNAL_ID);

		courseController.deleteCourse(DELETE_EXTERNAL_ID);

		verify(courseService).deleteCourse(DELETE_EXTERNAL_ID);
	}

	@Test
	public void filterCourse_expectOneCourse() {
		when(courseService.filterCourse(filterCourseRequest)).thenReturn(new PageImpl<>(Collections.singletonList(courseDto)));

		Page<CourseDto> courseDtoPage = courseController.filterCourse(filterCourseRequest);

		assertThat(courseDtoPage.getTotalElements(), is(1L));
		assertTrue(courseDtoPage.stream().findFirst().isPresent());
		assertEquals(courseDtoPage.stream().findFirst().get(), courseDto);
	}

	@Test
	public void getCourse_expectCourseDetails() {
		when(courseService.getCourseById(EXT_ID)).thenReturn(courseDetailsDto);

		CourseDetailsDto response = courseController.getCourse(EXT_ID);

		assertEquals(courseDetailsDto, response);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void getCourse_expectException() {
		when(courseService.getCourseById(EXT_ID)).thenThrow(ServiceExceptions.NotFoundException.class);

		courseController.getCourse(EXT_ID);
	}

	@Test
	public void getAllActiveCourses() {
		when(courseService.getAllActiveCourses()).thenReturn(Collections.singletonList(courseDto));

		List<CourseDto> response = courseController.getAllActiveCourses();

		assertThat(response.size(), is(1));
		assertTrue(response.contains(courseDto));
	}

	@Test
	public void getAllCourses() {
		when(courseService.getAllCourses()).thenReturn(Collections.singletonList(courseDto));

		List<CourseDto> response = courseController.getAllCourses();

		assertThat(response.size(), is(1));
		assertTrue(response.contains(courseDto));
	}

	@Test
	public void getBestCourses() {
		when(courseService.getBestCourses()).thenReturn(Collections.singletonList(courseDto));

		List<CourseDto> response = courseController.getBestCourses();

		assertThat(response.size(), is(1));
		assertTrue(response.contains(courseDto));
	}
}
