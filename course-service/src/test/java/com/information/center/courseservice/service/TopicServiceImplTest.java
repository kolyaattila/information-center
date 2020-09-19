package com.information.center.courseservice.service;

import com.information.center.courseservice.converter.TopicConverter;
import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.entity.TopicEntity;
import com.information.center.courseservice.model.request.TopicRequest;
import com.information.center.courseservice.model.response.TopicResponse;
import com.information.center.courseservice.repository.CourseRepository;
import com.information.center.courseservice.repository.TopicRepository;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceImplTest {

	private static final String NAME = "name";
	private static final String EXT_ID = "extId";
	private static final String AUTHOR = "author";
	private static final String DESCRIPTION_ENTITY = "descriptionEntity";
	private static final String NAME_ENTITY = "nameEntity";
	private static final String COURSE_EXT_ID = "courseExtId";
	@InjectMocks
	private TopicServiceImpl topicService;
	@Mock
	private TopicRepository topicRepository;
	@Mock
	private CourseRepository courseRepository;

	private TopicRequest topicRequest;
	private TopicEntity topicEntity;
	private TopicResponse topicResponse;
	private CourseEntity courseEntity;

	@Before
	public void setUp() {
		TopicConverter topicConverter = Mappers.getMapper(TopicConverter.class);
		topicService.setTopicConverter(topicConverter);

		topicRequest = TopicRequest.builder()
				.name(NAME)
				.externalId(EXT_ID)
				.courseExternalId(COURSE_EXT_ID)
				.build();

		topicEntity = new TopicEntity();
		topicEntity.setExternalId(EXT_ID);
		topicEntity.setName(NAME);

		topicResponse = new TopicResponse();
		topicResponse.setExternalId(EXT_ID);
		topicResponse.setName(NAME);

		courseEntity = new CourseEntity();
		courseEntity.setAuthor(AUTHOR);
		courseEntity.setCreated(new Date());
		courseEntity.setDescription(DESCRIPTION_ENTITY);
		courseEntity.setExternalId(EXT_ID);
		courseEntity.setName(NAME_ENTITY);
		courseEntity.setPrice(23);
	}

	@Test
	public void testCreate() {
		when(topicRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
		when(topicRepository.save(any())).then(r -> r.getArgument(0));
		when(courseRepository.findByExternalId(topicRequest.getCourseExternalId())).thenReturn(Optional.of(courseEntity));

		TopicResponse response = topicService.create(topicRequest);

		assertEquals(topicResponse.getName(), response.getName());
	}

	@Test(expected = InsertFailedException.class)
	public void testCreate_expectInsertException() {
		when(topicRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
		when(topicRepository.save(any())).thenThrow(HibernateException.class);
		when(courseRepository.findByExternalId(topicRequest.getCourseExternalId())).thenReturn(Optional.of(courseEntity));

		topicService.create(topicRequest);
	}

	@Test(expected = NotFoundException.class)
	public void testUpdate_expectNotFoundException() {
		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(empty());

		topicService.update(topicRequest);
	}

	@Test(expected = InsertFailedException.class)
	public void testUpdate_expectInsertFailedException() {
		TopicEntity topicToUpdate = new TopicEntity();
		topicToUpdate.setId(123);

		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(topicToUpdate));
		when(topicRepository.save(any())).thenThrow(HibernateException.class);

		topicService.update(topicRequest);
	}

	@Test
	public void testUpdate() {
		TopicEntity topicEntityToUpdate = new TopicEntity();
		topicEntityToUpdate.setId(123);

		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(topicEntityToUpdate));

		topicService.update(topicRequest);

		verify(topicRepository).save(any());
	}

	@Test
	public void testFindByExternalId() {
		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(topicEntity));

		TopicResponse response = topicService.findByExternalId(EXT_ID);

		assertEquals(topicResponse.getName(), response.getName());
		assertEquals(topicResponse.getExternalId(), response.getExternalId());
	}

	@Test(expected = NotFoundException.class)
	public void testFindByExternalId_expectNotFoundException() {
		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(empty());

		topicService.findByExternalId(EXT_ID);
	}

	@Test
	public void testFindAll_expectEmptyList() {
		when(topicRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

		List<TopicResponse> response = topicService.findAll();

		assertTrue(response.isEmpty());
	}

	@Test
	public void testFindAll() {
		when(topicRepository.findAll()).thenReturn(Collections.singletonList(topicEntity));

		List<TopicResponse> response = topicService.findAll();

		assertFalse(response.isEmpty());
		assertTrue(response.contains(topicResponse));
	}

	@Test(expected = NotFoundException.class)
	public void testDelete_expectNotFoundException() {
		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(empty());

		topicService.delete(EXT_ID);
	}

	@Test
	public void testDelete() {
		when(topicRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(topicEntity));

		topicService.delete(EXT_ID);

		verify(topicRepository).delete(topicEntity);
	}
}
