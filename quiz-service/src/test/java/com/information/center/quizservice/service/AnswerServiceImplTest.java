package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.AnswerConverter;
import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.entity.AnswerKey;
import com.information.center.quizservice.entity.AnswerType;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.repository.AnswerRepository;
import exception.ServiceExceptions;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceImplTest {
    public static final String ANSWER_ENTITY_EXTERNAL_ID = "answerEntityExternalId";
    public static final String ANSWER_REQUEST_EXTERNAL_ID = "answerRequestExternalId";

    @InjectMocks
    private AnswerServiceImpl answerService;
    @Mock
    private AnswerRepository answerRepository;
    private AnswerRequest answerRequest;
    private QuestionEntity questionEntity;
    private AnswerEntity answerEntity;

    @Before
    public void setUp() {
        answerService.setAnswerConverter(Mappers.getMapper(AnswerConverter.class));

        questionEntity = new QuestionEntity();
        answerRequest = AnswerRequest.builder()
                .externalId(ANSWER_REQUEST_EXTERNAL_ID)
                .correct(true)
                .key(AnswerKey.C)
                .reason("reason")
                .name("name")
                .answerType(AnswerType.CHEMISTRY_ANSWER)
                .parseText("parseText")
                .build();
        answerEntity = new AnswerEntity();
        answerEntity.setExternalId(ANSWER_ENTITY_EXTERNAL_ID);
    }

    @Test
    public void saveEntity() {
        when(answerRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(answerRepository.save(any())).then(r -> r.getArgument(0));

        AnswerEntity response = answerService.saveEntity(answerRequest, questionEntity);

        assertEquals(response.getQuestion(), questionEntity);
        assertFalse(response.getExternalId().isEmpty());
        assertEquals(response.getName(), answerRequest.getName());
        assertEquals(response.getReason(), answerRequest.getReason());
        assertEquals(response.getKey(), answerRequest.getKey());
        assertEquals(response.getParseText(), answerRequest.getParseText());
        assertEquals(response.getAnswerType(), answerRequest.getAnswerType());
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void saveEntity_expectInsertFailedException() {
        when(answerRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(answerRepository.save(any())).thenThrow(HibernateException.class);

        answerService.saveEntity(answerRequest, questionEntity);
    }

    @Test
    public void getUpdatedEntities_expectToDeleteAnswer() {
        questionEntity.setAnswers(Collections.singletonList(answerEntity));
        List<AnswerEntity> response = answerService.getUpdatedEntities(Collections.emptyList(), questionEntity);

        assertTrue(response.isEmpty());
        verify(answerRepository).delete(answerEntity);
    }

    @Test
    public void getUpdatedEntities_expectToCreateNewEntity() {
        questionEntity.setAnswers(Collections.singletonList(answerEntity));
        List<AnswerEntity> response = answerService.getUpdatedEntities(Collections.singletonList(answerRequest), questionEntity);

        verify(answerRepository).delete(answerEntity);
        assertFalse(response.isEmpty());
        AnswerEntity entityResponse = response.get(0);
        assertEquals(entityResponse.getQuestion(), questionEntity);
        assertNotEquals(ANSWER_REQUEST_EXTERNAL_ID, entityResponse.getExternalId());
        assertNotEquals(ANSWER_ENTITY_EXTERNAL_ID, entityResponse.getExternalId());
        assertEquals(entityResponse.getName(), answerRequest.getName());
        assertEquals(entityResponse.getReason(), answerRequest.getReason());
        assertEquals(entityResponse.getKey(), answerRequest.getKey());
        assertEquals(entityResponse.getAnswerType(), answerRequest.getAnswerType());
        assertEquals(entityResponse.getParseText(), answerRequest.getParseText());
    }

    @Test
    public void getUpdatedEntities_expectToUpdateEntityValues() {
        answerRequest.setExternalId(ANSWER_ENTITY_EXTERNAL_ID);
        questionEntity.setAnswers(Collections.singletonList(answerEntity));

        when(answerRepository.findByExternalId(ANSWER_ENTITY_EXTERNAL_ID)).thenReturn(Optional.of(answerEntity));

        List<AnswerEntity> response = answerService.getUpdatedEntities(Collections.singletonList(answerRequest), questionEntity);

        verify(answerRepository, never()).delete(answerEntity);
        assertFalse(response.isEmpty());
        AnswerEntity entityResponse = response.get(0);
        assertEquals(ANSWER_ENTITY_EXTERNAL_ID, entityResponse.getExternalId());
        assertEquals(entityResponse.getName(), answerRequest.getName());
        assertEquals(entityResponse.getReason(), answerRequest.getReason());
        assertEquals(entityResponse.getKey(), answerRequest.getKey());
        assertEquals(entityResponse.getAnswerType(), answerRequest.getAnswerType());
        assertEquals(entityResponse.getParseText(), answerRequest.getParseText());
    }
}
