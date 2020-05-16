package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import com.information.center.quizservice.service.SchoolService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchoolControllerTest {

    private static final String EXTERNAL_ID = "externalId";
    @InjectMocks
    private SchoolController schoolController;
    @Mock
    private SchoolService schoolService;
    private SchoolRequest schoolRequest;
    private SchoolDto schoolDto;

    @Before
    public void setUp() {
        schoolRequest = SchoolRequest.builder()
                .externalId(EXTERNAL_ID)
                .name("name")
                .numberOfQuestions(23)
                .build();
        schoolDto = SchoolDto.builder()
                .externalId(EXTERNAL_ID)
                .name("name")
                .numberOfQuestions(35)
                .build();
    }

    @Test
    public void testCreate() {
        when(schoolService.create(schoolRequest)).thenReturn(schoolDto);

        SchoolDto response = schoolController.create(schoolRequest);

        assertEquals(schoolDto, response);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void testCreateWhenServiceThrow_expectBadRequest() {
        when(schoolService.create(schoolRequest)).thenThrow(ServiceExceptions.InsertFailedException.class);

        schoolController.create(schoolRequest);
    }

    @Test
    public void testUpdate() {
        schoolController.update(schoolRequest);

        verify(schoolService).update(schoolRequest);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void testUpdateWhenServiceThrowInsertFailedException_expectBadRequest() {
        doThrow(ServiceExceptions.InsertFailedException.class).when(schoolService).update(schoolRequest);
        schoolController.update(schoolRequest);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void testUpdateWhenServiceThrowNotFoundException_expectBadRequest() {
        doThrow(ServiceExceptions.NotFoundException.class).when(schoolService).update(schoolRequest);
        schoolController.update(schoolRequest);
    }

    @Test
    public void testFindByExternalId() {
        when(schoolService.findByExternalId(EXTERNAL_ID)).thenReturn(schoolDto);

        SchoolDto response = schoolController.findByExternalId(EXTERNAL_ID);

        assertEquals(schoolDto, response);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void testFindByExternalId_expectBadRequest() {
        when(schoolService.findByExternalId(EXTERNAL_ID)).thenThrow(ServiceExceptions.NotFoundException.class);

        schoolController.findByExternalId(EXTERNAL_ID);
    }

    @Test
    public void testFindAll() {
        when(schoolService.findAll()).thenReturn(Collections.singletonList(schoolDto));

        List<SchoolDto> response = schoolController.findAll();

        assertTrue(response.contains(schoolDto));
    }

    @Test
    public void testDelete() {
        schoolController.delete(EXTERNAL_ID);

        verify(schoolService).delete(EXTERNAL_ID);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void testDelete_expectBadRequest() {
        doThrow(ServiceExceptions.NotFoundException.class).when(schoolService).delete(EXTERNAL_ID);

        schoolController.delete(EXTERNAL_ID);
    }
}
