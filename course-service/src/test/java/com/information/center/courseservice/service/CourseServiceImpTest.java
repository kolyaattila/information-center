package com.information.center.courseservice.service;

import com.information.center.courseservice.converter.CourseConverter;
import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.request.CourseRequest;
import com.information.center.courseservice.model.request.FilterCourseRequest;
import com.information.center.courseservice.repository.CourseRepository;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImpTest {

	private static final String USERNAME = "username";
	private static final String EXT_ID = "extId";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String AUTHOR = "author";
	private static final String DESCRIPTION_ENTITY = "descriptionEntity";
	private static final String NAME_ENTITY = "nameEntity";
	@InjectMocks
	private CourseServiceImp courseServiceImp;
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private ReviewService reviewService;
	private CourseRequest courseRequest;
	private CourseEntity courseEntity;
	private FilterCourseRequest filterCourseRequest;
	private final ArgumentCaptor<CourseEntity> captor = ArgumentCaptor.forClass(CourseEntity.class);

	@Before
	public void setUp() {
		CourseConverter courseConverter = Mappers.getMapper(CourseConverter.class);
		courseServiceImp.setCourseConverter(courseConverter);

		courseRequest = CourseRequest.builder()
				.price(3)
				.name(NAME)
				.enable(false)
				.description(DESCRIPTION)
				.externalId(EXT_ID)
				.build();

		courseEntity = new CourseEntity();
		courseEntity.setAuthor(AUTHOR);
		courseEntity.setCreated(new Date());
		courseEntity.setDescription(DESCRIPTION_ENTITY);
		courseEntity.setExternalId(EXT_ID);
		courseEntity.setName(NAME_ENTITY);
		courseEntity.setPrice(23);

		filterCourseRequest = FilterCourseRequest.builder()
				.enable(false)
				.externalId(EXT_ID)
				.name(NAME)
				.pageNumber(33)
				.price(34)
				.pageSize(12)
				.build();
	}

	@Test
	public void saveCourse_expectToSaveCourse() {
		when(courseRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(USERNAME, ""));

		courseServiceImp.saveCourse(courseRequest);

		verify(courseRepository).save(captor.capture());
		CourseEntity value = captor.getValue();

		assertEquals(courseRequest.getDescription(), value.getDescription());
		assertEquals(courseRequest.getName(), value.getName());
		assertEquals(courseRequest.getPrice(), value.getPrice(), 1);
		assertEquals(courseRequest.isEnable(), value.isEnable());
		assertEquals(USERNAME, value.getAuthor());
		assertFalse(value.getExternalId().isEmpty());
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void updateCourse_expectException() {
		when(courseRepository.findByExternalId(EXT_ID)).thenReturn(Optional.empty());

		courseServiceImp.updateCourse(courseRequest);
	}

	@Test
	public void updateCourse_expectToUpdateCourse() {
		when(courseRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(courseEntity));

		courseServiceImp.updateCourse(courseRequest);

		verify(courseRepository).save(captor.capture());
		CourseEntity value = captor.getValue();

		assertEquals(courseRequest.getDescription(), value.getDescription());
		assertEquals(courseRequest.getPrice(), value.getPrice(), 1);
		assertEquals(courseRequest.getName(), value.getName());
		assertEquals(courseRequest.isEnable(), value.isEnable());
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void deleteCourse_expectException() {
		when(courseRepository.findByExternalId(EXT_ID)).thenReturn(Optional.empty());

		courseServiceImp.deleteCourse(EXT_ID);
	}

	@Test
	public void deleteCourse_expectToDeleteCourse() {
		when(courseRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(courseEntity));

		courseServiceImp.deleteCourse(EXT_ID);

		verify(courseRepository).delete(courseEntity);
	}

	@Test
	public void filterCourse() {
		when(courseRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl(Collections.singletonList(courseEntity)));

		Page<CourseDto> courseDtoPage = courseServiceImp.filterCourse(filterCourseRequest);

		assertThat(courseDtoPage.getTotalElements(), is(1L));
		assertTrue(courseDtoPage.stream().findFirst().isPresent());
		CourseDto response = courseDtoPage.stream().findFirst().get();

		assertThat(response.getAuthor(), is(courseEntity.getAuthor()));
		assertThat(response.getDescription(), is(courseEntity.getDescription()));
		assertThat(response.getCreated(), is(courseEntity.getCreated()));
		assertThat(response.getPrice(), is(courseEntity.getPrice()));
		assertThat(response.getName(), is(courseEntity.getName()));
		assertThat(response.getExternalId(), is(courseEntity.getExternalId()));
		verify(reviewService).getRating(courseEntity);
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void getCourseById_expectException() {
		when(courseRepository.findByExternalId(EXT_ID)).thenReturn(Optional.empty());

		courseServiceImp.getCourseById(EXT_ID);
	}

	@Test
	public void getCourseById_expectCourse() {
		when(courseRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(courseEntity));

		CourseDetailsDto response = courseServiceImp.getCourseById(EXT_ID);

		assertThat(response.getAuthor(), is(courseEntity.getAuthor()));
		assertThat(response.getDescription(), is(courseEntity.getDescription()));
		assertThat(response.getCreated(), is(courseEntity.getCreated()));
		assertThat(response.getPrice(), is(courseEntity.getPrice()));
		assertThat(response.getName(), is(courseEntity.getName()));
		assertThat(response.getExternalId(), is(courseEntity.getExternalId()));
	}

	@Test
	public void getAllActiveCourses() {
		when(courseRepository.findAllByEnable(true)).thenReturn(Collections.singletonList(courseEntity));

		List<CourseDto> allActiveCourses = courseServiceImp.getAllActiveCourses();

		assertThat(allActiveCourses.size(), is(1));
		CourseDto response = allActiveCourses.get(0);
		assertThat(response.getAuthor(), is(courseEntity.getAuthor()));
		assertThat(response.getDescription(), is(courseEntity.getDescription()));
		assertThat(response.getCreated(), is(courseEntity.getCreated()));
		assertThat(response.getPrice(), is(courseEntity.getPrice()));
		assertThat(response.getName(), is(courseEntity.getName()));
		assertThat(response.getExternalId(), is(courseEntity.getExternalId()));
	}

	@Test
	public void getAllCourses() {
		when(courseRepository.findAll()).thenReturn(Collections.singletonList(courseEntity));

		List<CourseDto> allCourses = courseServiceImp.getAllCourses();

		assertThat(allCourses.size(), is(1));
		CourseDto response = allCourses.get(0);
		assertThat(response.getAuthor(), is(courseEntity.getAuthor()));
		assertThat(response.getDescription(), is(courseEntity.getDescription()));
		assertThat(response.getCreated(), is(courseEntity.getCreated()));
		assertThat(response.getPrice(), is(courseEntity.getPrice()));
		assertThat(response.getName(), is(courseEntity.getName()));
		assertThat(response.getExternalId(), is(courseEntity.getExternalId()));
	}

	@Test
	public void getBestCourses() {
		when(courseRepository.bestCourses()).thenReturn(Collections.singletonList(courseEntity));

		List<CourseDto> bestCourses = courseServiceImp.getBestCourses();

		assertThat(bestCourses.size(), is(1));
		CourseDto response = bestCourses.get(0);
		assertThat(response.getAuthor(), is(courseEntity.getAuthor()));
		assertThat(response.getDescription(), is(courseEntity.getDescription()));
		assertThat(response.getCreated(), is(courseEntity.getCreated()));
		assertThat(response.getPrice(), is(courseEntity.getPrice()));
		assertThat(response.getName(), is(courseEntity.getName()));
		assertThat(response.getExternalId(), is(courseEntity.getExternalId()));
	}
}
