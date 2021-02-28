package com.information.center.quizservice.controller;

import com.information.center.quizservice.entity.QuestionDifficulty;
import com.information.center.quizservice.entity.QuestionType;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.QuestionResponseValidated;
import com.information.center.quizservice.model.request.FilterQuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequestValidation;
import com.information.center.quizservice.service.QuestionService;
import com.information.center.quizservice.service.QuestionValidateService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest {

    @InjectMocks
    private QuestionController questionController;
    @Mock
    private QuestionService questionService;
    @Mock
    private QuestionValidateService questionValidateService;
    private QuestionRequestValidation questionRequestValidation;
    private QuestionResponseValidated questionResponseValidated;
    private QuestionRequest questionRequest;
    private QuestionDto questionDto;
    private FilterQuestionRequest filterQuestionRequest;

    @Before
    public void setUp() {
        questionRequestValidation = QuestionRequestValidation.builder().build();
        questionResponseValidated = QuestionResponseValidated.builder().build();
        questionRequest = QuestionRequest.builder()
                .book("book")
                .chapterExternalId("chapterExternalId")
                .externalId("externalId")
                .name("name")
                .verified(true)
                .questionNumber(1412)
                .courseExternalId("courseExternalId")
                .questionDifficulty(QuestionDifficulty.EASY)
                .questionType(QuestionType.CHEMISTRY_QUESTION)
                .parseText("parseText").build();
        questionDto = QuestionDto.builder()
                .book("book")
                .chapterExternalId("chapterExternalId")
                .externalId("externalId")
                .name("name")
                .verified(true)
                .questionNumber(1412)
                .courseExternalId("courseExternalId")
                .questionDifficulty(QuestionDifficulty.EASY)
                .questionType(QuestionType.CHEMISTRY_QUESTION)
                .parseText("parseText")
                .build();
        filterQuestionRequest = FilterQuestionRequest.builder()
                .book("book")
                .chapterExternalId("chapterExternalId")
                .externalId("externalId")
                .name("name")
                .pageSize(2)
                .verified(true)
                .questionNumber(1412)
                .courseExternalId("courseExternalId")
                .questionDifficulty(QuestionDifficulty.EASY)
                .pageNumber(2)
                .build();
    }


    @Test
    public void create() {
        when(questionService.create(questionRequest)).thenReturn(questionDto);

        QuestionDto response = questionController.create(questionRequest);

        assertEquals(questionDto, response);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void createWhenServiceThrowInsertFailedException_expectBadRequest() {
        when(questionService.create(questionRequest)).thenThrow(ServiceExceptions.InsertFailedException.class);

        questionController.create(questionRequest);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void createWhenServiceThrowNotFoundException_expectBadRequest() {
        when(questionService.create(questionRequest)).thenThrow(ServiceExceptions.NotFoundException.class);

        questionController.create(questionRequest);
    }

    @Test
    public void update() {
        questionController.update(questionRequest);

        verify(questionService).update(questionRequest);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void updateWhenServiceThrowInsertFailedException_expectBadRequest() {
        doThrow(ServiceExceptions.InsertFailedException.class).when(questionService).update(questionRequest);

        questionController.update(questionRequest);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void updateWhenServiceThrowNotFoundException_expectBadRequest() {
        doThrow(ServiceExceptions.NotFoundException.class).when(questionService).update(questionRequest);

        questionController.update(questionRequest);
    }


    @Test
    public void delete() {
        questionController.delete("externalId");

        verify(questionService).delete("externalId");
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void delete_expectBadRequest() {
        doThrow(ServiceExceptions.NotFoundException.class).when(questionService).delete("externalId");

        questionController.delete("externalId");
    }


    @Test
    public void findByExternalId() {
        when(questionService.findByExternalId("externalId")).thenReturn(questionDto);

        QuestionDto response = questionController.findByExternalId("externalId");

        assertEquals(questionDto, response);
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void findByExternalId_expectBadRequest() {
        when(questionService.findByExternalId("externalId")).thenThrow(ServiceExceptions.NotFoundException.class);

        questionController.findByExternalId("externalId");
    }

    @Test
    public void test() {
        PageImpl<QuestionDto> page = new PageImpl<>(Collections.singletonList(questionDto));
        when(questionService.filterQuestions(filterQuestionRequest)).thenReturn(page);

        Page<QuestionDto> response = questionController.filterQuestion(filterQuestionRequest);

        assertEquals(page, response);
    }
}
