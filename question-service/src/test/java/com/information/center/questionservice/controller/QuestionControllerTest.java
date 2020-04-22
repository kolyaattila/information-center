package com.information.center.questionservice.controller;

import com.information.center.questionservice.model.QuestionListDetails;
import com.information.center.questionservice.model.QuestionResponseValidated;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.model.response.QuestionResponsePage;
import com.information.center.questionservice.service.QuestionService;
import com.information.center.questionservice.service.QuestionValidateService;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private QuestionResponse questionResponse;

    @Before
    public void setUp() {
        questionRequest = QuestionRequest.builder().build();
        questionResponse = QuestionResponse.builder().build();
        questionRequestValidation = QuestionRequestValidation.builder().build();
        questionResponseValidated = QuestionResponseValidated.builder().build();
    }

    @Test
    public void create() {
        when(questionService.create(questionRequest, "topicExternalId")).thenReturn(questionResponse);

        QuestionResponse response = questionController
                .create(questionRequest, "topicExternalId");

        assertEquals(questionResponse, response);
    }

    @Test
    public void update() {
        questionController.update(questionRequest);

        verify(questionService).update(questionRequest);
    }

    @Test
    public void delete() {
        questionController.delete("externalId");

        verify(questionService).delete("externalId");
    }

    @Test
    public void findByExternalId() {
        when(questionService.findByExternalId("externalId")).thenReturn(questionResponse);

        QuestionResponse response = questionController.findByExternalId("externalId");

        assertEquals(questionResponse, response);
    }

    @Test
    public void findQuestionsByTopicId() {
        PageRequest pageRequest = new PageRequest(1, 1);

        when(questionService.findQuestionsByTopicId("externalId", pageRequest))
                .thenReturn(new QuestionListDetails(new PageImpl<>(Collections.singletonList(questionResponse)), new Date(), ""));

        var response = questionController
                .findQuestionsByTopicId("externalId", pageRequest);

        assertEquals(1, response.getQuestionResponseList().getTotalElements());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(1, 1);

        when(questionService.findAll(pageRequest))
                .thenReturn(new QuestionResponsePage(new Date(), new PageImpl<>(Collections.singletonList(questionResponse))));

        var response = questionController.findAll(pageRequest);

        assertEquals(1, response.getQuestionResponses().getTotalElements());
    }

  @Test
  public void validate() {
    when(questionValidateService.validate(questionRequestValidation)).thenReturn(questionResponseValidated);

    var response = questionController.validate(questionRequestValidation);

    assertEquals(questionResponseValidated, response);
  }
}
