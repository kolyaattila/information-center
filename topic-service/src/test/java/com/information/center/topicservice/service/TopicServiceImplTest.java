package com.information.center.topicservice.service;


import com.information.center.topicservice.converter.TopicConverter;
import com.information.center.topicservice.entity.TopicEntity;
import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import com.information.center.topicservice.repository.TopicRepository;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceImplTest {

    private static final String NAME = "name";
    private static final String EXT_ID = "extId";
    @InjectMocks
    private TopicServiceImpl topicService;
    @Mock
    private TopicConverter topicConverter;
    @Mock
    private TopicRepository topicRepository;
    private TopicRequest topicRequest;
    private TopicEntity topicEntity;
    private TopicResponse topicResponse;

    @Before
    public void setUp() {
        topicRequest = TopicRequest.builder()
                .name(NAME)
                .externalId(EXT_ID)
                .build();

        topicEntity = new TopicEntity();
        topicEntity.setExternalId(EXT_ID);
        topicEntity.setName(NAME);

        topicResponse = new TopicResponse();
        topicResponse.setExternalId(EXT_ID);
        topicResponse.setName(NAME);
    }

    @Test
    public void testCreate() {
        when(topicConverter.toEntity(topicRequest)).thenReturn(topicEntity);
        when(topicRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(topicRepository.save(topicEntity)).then(r -> r.getArgument(0));
        when(topicConverter.toResponse(topicEntity)).thenReturn(topicResponse);

        TopicResponse response = topicService.create(topicRequest);

        assertEquals(topicResponse, response);
    }

    @Test(expected = InsertFailedException.class)
    public void testCreate_expectInsertException() {
        when(topicConverter.toEntity(topicRequest)).thenReturn(topicEntity);
        when(topicRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(topicRepository.save(topicEntity)).thenThrow(HibernateException.class);

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
        when(topicConverter.toEntity(topicRequest, topicToUpdate.getId())).thenReturn(topicEntity);
        when(topicRepository.save(topicEntity)).thenThrow(HibernateException.class);

        topicService.update(topicRequest);
    }

    @Test
    public void testUpdate() {
        TopicEntity topicEntityToUpdate = new TopicEntity();
        topicEntityToUpdate.setId(123);

        when(topicRepository.findByExternalId(EXT_ID)).thenReturn(Optional.of(topicEntityToUpdate));
        when(topicConverter.toEntity(topicRequest, topicEntityToUpdate.getId()))
                .thenReturn(topicEntity);

        topicService.update(topicRequest);

        verify(topicRepository).save(topicEntity);
    }

    @Test
    public void testFindByExternalId() {
        when(topicConverter.toResponse(topicEntity)).thenReturn(topicResponse);
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
        verify(topicConverter, never()).toResponse(any());
    }

    @Test
    public void testFindAll() {
        when(topicRepository.findAll()).thenReturn(Collections.singletonList(topicEntity));
        when(topicConverter.toResponse(topicEntity)).thenReturn(topicResponse);

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
