package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.QuizConverter;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.entity.QuizEntity;
import com.information.center.quizservice.entity.QuizType;
import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.request.QuizRequest;
import com.information.center.quizservice.repository.QuestionRepository;
import com.information.center.quizservice.repository.QuizRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class QuizServiceImplTest {

    @InjectMocks
    private QuizServiceImpl quizService;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @Captor
    private ArgumentCaptor<QuizEntity> captor;
    private QuizRequest quizRequest;
    private SchoolEntity schoolEntity;
    private QuestionEntity questionEntity;
    private QuizEntity quizEntity;

    @Before
    public void setUp() {
        QuizConverter quizConverter = Mappers.getMapper(QuizConverter.class);
        quizService.setQuizConverter(quizConverter);

        quizEntity = new QuizEntity();
        quizEntity.setQuestions(Collections.emptyList());
        questionEntity = new QuestionEntity();
        schoolEntity = new SchoolEntity();
        quizRequest = QuizRequest.builder()
                .schoolExternalId("schoolExternalId")
                .questionIds(Arrays.asList("question1", "question2"))
                .type(QuizType.CHAPTER)
                .externalId("externalId")
                .build();
    }


    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void testCreateQuiz_expectSchoolNotFound() {
        when(schoolRepository.findByExternalId("schoolExternalId")).thenReturn(Optional.empty());

        quizService.createQuiz(quizRequest);
    }

    @Test(expected = ServiceExceptions.WrongQuizType.class)
    public void testCreateQuiz_expectQuizWrongType() {
        quizRequest = QuizRequest.builder()
                .type(QuizType.EXAM).build();

        quizService.createQuiz(quizRequest);
    }

    @Test
    public void testCreateQuiz_expectQuizCreated() {
        when(schoolRepository.findByExternalId("schoolExternalId")).thenReturn(Optional.of(schoolEntity));
        when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
        when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());
        when(quizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

        quizService.createQuiz(quizRequest);

        verify(quizRepository).save(captor.capture());
        QuizEntity response = captor.getValue();
        assertNotNull(response);
        assertEqualsEntity(response, quizRequest);
        assertTrue(response.getQuestions().contains(questionEntity));
        assertEquals(response.getSchool(), schoolEntity);
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void testCreateQuiz_expectQuizNotCreated() {
        when(schoolRepository.findByExternalId("schoolExternalId")).thenReturn(Optional.of(schoolEntity));
        when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
        when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());
        when(quizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
        when(quizRepository.save(any())).thenThrow(HibernateException.class);

        quizService.createQuiz(quizRequest);
    }


    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void testUpdateQuiz_expectNotFountException() {
        when(quizRepository.findByChapterExternalId("externalId")).thenReturn(Optional.empty());

        quizService.updateQuiz(quizRequest);
    }

    @Test
    public void testUpdateQuiz_expectQuizToBeUpdated() {
        when(quizRepository.findByChapterExternalId("externalId")).thenReturn(Optional.of(quizEntity));
        when(schoolRepository.findByExternalId("schoolExternalId")).thenReturn(Optional.of(schoolEntity));
        when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
        when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());

        quizService.updateQuiz(quizRequest);

        verify(quizRepository).save(captor.capture());
        QuizEntity response = captor.getValue();
        assertEquals(response.getSchool(), schoolEntity);
        assertTrue(response.getQuestions().contains(questionEntity));
        assertEqualsEntity(response, quizRequest);
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void testUpdateQuiz_expectInsertFailedException() {
        when(quizRepository.findByChapterExternalId("externalId")).thenReturn(Optional.of(quizEntity));
        when(schoolRepository.findByExternalId("schoolExternalId")).thenReturn(Optional.of(schoolEntity));
        when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
        when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());
        when(quizRepository.save(any())).thenThrow(HibernateException.class);

        quizService.updateQuiz(quizRequest);

    }


    @Test(expected = ServiceExceptions.WrongQuizType.class)
    public void testUpdateQuiz_expectQuizWrongType() {
        quizRequest = QuizRequest.builder().type(QuizType.EXAM).build();

        quizService.updateQuiz(quizRequest);
    }

    @Test
    public void testGetAll_expectEmptyList() {
        when(quizRepository.findAll()).thenReturn(Collections.emptyList());

        List<QuizDto> response = quizService.getAll();

        assertTrue(response.isEmpty());
    }

    @Test
    public void testGetAll_expectQuizDtoList() {
        when(quizRepository.findAll()).thenReturn(Collections.singletonList(quizEntity));

        List<QuizDto> response = quizService.getAll();

        assertFalse(response.isEmpty());
    }

    private void assertEqualsEntity(QuizEntity entity, QuizRequest quizRequest) {
        assertEquals(quizRequest.getChapterExternalId(), entity.getChapterExternalId());
        assertEquals(quizRequest.getCourseExternalId(), entity.getCourseExternalId());
        assertEquals(quizRequest.getEnable(), entity.getEnable());
        assertEquals(quizRequest.getType(), entity.getType());
    }
}
