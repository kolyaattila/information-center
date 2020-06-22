package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.request.TopicRequest;
import com.information.center.courseservice.model.response.TopicResponse;
import com.information.center.courseservice.service.TopicService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class TopicControllerTest {

    private static final String EXT_ID = "extId";
    @InjectMocks
    private TopicController topicController;
    @Mock
    private TopicService topicService;
    private TopicRequest topicRequest;
    private TopicResponse topicResponse;

    @Before
    public void setUp() {
        topicRequest = new TopicRequest();
        topicRequest.setName("name");
        topicRequest.setExternalId(EXT_ID);
        topicResponse = new TopicResponse();
    }

//    @Test
//    public void testCreate() {
//        when(topicService.create(topicRequest)).thenReturn(topicResponse);
//
//        TopicResponse response = topicController.create(topicRequest);
//
//        assertEquals(topicResponse, response);
//    }
//
//    @Test(expected = BadRequest.class)
//    public void testCreateWhenServiceThrowInsertFaildException_expectBadRequest() {
//        when(topicService.create(topicRequest)).thenThrow(InsertFailedException.class);
//
//        topicController.create(topicRequest);
//    }
//
//    @Test
//    public void testUpdate() {
//        topicController.update(topicRequest);
//
//        verify(topicService).update(topicRequest);
//    }
//
//    @Test(expected = BadRequest.class)
//    public void testUpdateWhenServiceThrowNotFoundException_expectBadRequest() {
//        doThrow(NotFoundException.class).when(topicService).update(topicRequest);
//
//        topicController.update(topicRequest);
//    }
//
//    @Test(expected = BadRequest.class)
//    public void testUpdateWhenServiceThrowInsertFailedException_expectBadRequest() {
//        doThrow(InsertFailedException.class).when(topicService).update(topicRequest);
//
//        topicController.update(topicRequest);
//    }
//
//    @Test
//    public void testFindByExternalId() {
//        when(topicService.findByExternalId(EXT_ID)).thenReturn(topicResponse);
//
//        TopicResponse response = topicController.findByExternalId(EXT_ID);
//
//        assertEquals(topicResponse, response);
//    }
//
//    @Test(expected = BadRequest.class)
//    public void testFindByExternalIdWhenServiceThrowNotFoundException_expectBadRequest() {
//        when(topicService.findByExternalId(EXT_ID)).thenThrow(NotFoundException.class);
//
//        topicController.findByExternalId(EXT_ID);
//    }
//
//    @Test
//    public void testFindAll() {
//        when(topicService.findAll()).thenReturn(Collections.singletonList(topicResponse));
//
//        List<TopicResponse> responses = topicController.findAll();
//
//        assertFalse(responses.isEmpty());
//        assertTrue(responses.contains(topicResponse));
//    }
//
//    @Test
//    public void testDelete() {
//        topicController.delete(EXT_ID);
//
//        verify(topicService).delete(EXT_ID);
//    }
//
//    @Test(expected = BadRequest.class)
//    public void testDeleteWhenServiceThrowNotFoundException_expectBadRequest() {
//        doThrow(NotFoundException.class).when(topicService).delete(EXT_ID);
//
//        topicController.delete(EXT_ID);
//    }
}
