package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.SchoolConverter;
import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import com.information.center.quizservice.repository.SchoolRepository;
import exception.ServiceExceptions;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SchoolServiceImplTest {

    private static final String EXTERNAL_ID = "externalId";
    private static final String NAME = "name";
    @InjectMocks
    private SchoolServiceImpl schoolService;
    @Mock
    private SchoolRepository schoolRepository;
    @Captor
    private ArgumentCaptor<SchoolEntity> captor;
    private SchoolRequest schoolRequest;
    private SchoolEntity schoolEntity;

    @Before
    public void setUp() {
        SchoolConverter schoolConverter = Mappers.getMapper(SchoolConverter.class);
        schoolService.setConverter(schoolConverter);

        schoolEntity = new SchoolEntity();
        schoolRequest = SchoolRequest.builder()
                .numberOfQuestions(23)
                .name(NAME)
                .externalId(EXTERNAL_ID)
                .build();
    }

    @Test
    public void testCreate_expectToCreate() {
        when(schoolRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(schoolRepository.save(any())).then(r -> r.getArgument(0));

        SchoolDto schoolDto = schoolService.create(schoolRequest);

        assertNotNull(schoolDto);
        assertEquals(schoolDto.getName(), schoolRequest.getName());
        assertEquals(schoolDto.getNumberOfQuestions(), schoolRequest.getNumberOfQuestions());
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void testCreate_expectInsertFailedException() {
        when(schoolRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(schoolRepository.save(any())).thenThrow(HibernateException.class);

        schoolService.create(schoolRequest);
    }


    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void testUpdate_expectNotFoundException() {
        when(schoolRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

        schoolService.update(schoolRequest);
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void testUpdate_expectInsertFailedException() {
        when(schoolRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
        when(schoolRepository.save(any())).thenThrow(HibernateException.class);

        schoolService.update(schoolRequest);
    }

    @Test
    public void testUpdate_expectToUpdate() {
        when(schoolRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));

        schoolService.update(schoolRequest);

        verify(schoolRepository).save(captor.capture());
        SchoolEntity response = captor.getValue();
        assertNotNull(response);
        assertEquals(response.getName(), schoolRequest.getName());
        assertEquals(response.getNumberOfQuestions(), schoolRequest.getNumberOfQuestions());
    }

    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void testFindByExternalId_expectNotFoundException() {
        when(schoolRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

        schoolService.findByExternalId(EXTERNAL_ID);
    }

    @Test
    public void testFindByExternalId() {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setName(NAME);
        schoolEntity.setNumberOfQuestions(23);

        when(schoolRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));

        SchoolDto response = schoolService.findByExternalId(EXTERNAL_ID);

        assertNotNull(response);
        assertEquals(NAME, response.getName());
        assertEquals(23, response.getNumberOfQuestions());
    }

    @Test
    public void testFindAll_expectEmptyList() {
        List<SchoolDto> response = schoolService.findAll();

        assertTrue(response.isEmpty());
    }

    @Test
    public void testFindAll_expectSchoolList() {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setName(NAME);
        schoolEntity.setNumberOfQuestions(23);

        when(schoolRepository.findAll()).thenReturn(Collections.singletonList(schoolEntity));

        List<SchoolDto> response = schoolService.findAll();

        assertFalse(response.isEmpty());
        SchoolDto dto = response.get(0);
        assertNotNull(dto);
        assertEquals(NAME, dto.getName());
        assertEquals(23, dto.getNumberOfQuestions());
    }

    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void testDelete_expectNotFoundException() {

        schoolService.delete(EXTERNAL_ID);
    }

    @Test
    public void testDelete_expectToDelete() {
        when(schoolRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));

        schoolService.delete(EXTERNAL_ID);

        verify(schoolRepository).delete(schoolEntity);
    }
}
