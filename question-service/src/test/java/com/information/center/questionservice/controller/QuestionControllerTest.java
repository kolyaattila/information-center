//package com.information.center.questionservice.controller;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.information.center.questionservice.model.QuestionListDetails;
//import com.information.center.questionservice.model.request.QuestionRequest;
//import com.information.center.questionservice.model.response.QuestionResponse;
//import com.information.center.questionservice.service.QuestionService;
//import java.util.Collections;
//import java.util.Date;
//
//import lombok.var;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//@RunWith(MockitoJUnitRunner.class)
//public class QuestionControllerTest {
//
//  @InjectMocks
//  private QuestionController questionController;
//  @Mock
//  private QuestionService questionService;
//  private QuestionRequest questionRequest;
//  private QuestionResponse questionResponse;
//
//  @Before
//  public void setUp() {
//    questionRequest = QuestionRequest.builder().build();
//    questionResponse = QuestionResponse.builder().build();
//  }
//
//  @Test
//  public void create() {
//    when(questionService.create(questionRequest, "topicExternalId")).thenReturn(questionResponse);
//
//    QuestionResponse response = questionController
//        .create(questionRequest, "topicExternalId");
//
//    assertEquals(questionResponse, response);
//  }
//
//  @Test
//  public void update() {
//    questionController.update(questionResponse);
//
//    verify(questionService).update(questionResponse);
//  }
//
//  @Test
//  public void delete() {
//    questionController.delete("externalId");
//
//    verify(questionService).delete("externalId");
//  }
//
//  @Test
//  public void findByExternalId() {
//    when(questionService.findByExternalId("externalId")).thenReturn(questionResponse);
//
//    QuestionResponse response = questionController.findByExternalId("externalId");
//
//    assertEquals(questionResponse, response);
//  }
//
//  @Test
//  public void findQuestionsByTopicId() {
//    PageRequest pageRequest = new PageRequest(1, 1);
//
//    when(questionService.findQuestionsByTopicId("externalId", pageRequest))
//        .thenReturn(new QuestionListDetails(new PageImpl<>(Collections.singletonList(questionResponse),new Date(),"topicName")));
//
//    QuestionListDetails response = questionController
//            .findQuestionsByTopicId("externalId", pageRequest);
//
//    assertEquals(1, response.getQuestionResponseList().getTotalElements());
//  }
//
//  @Test
//  public void findAll() {
//    PageRequest pageRequest = new PageRequest(1, 1);
//
//    when(questionService.findAll(pageRequest))
//        .thenReturn(new PageImpl<>(Collections.singletonList(questionResponse)));
//
//    Page<QuestionResponse> response = questionController.findAll(pageRequest);
//
//    assertEquals(1, response.getTotalElements());
//  }
//
//  @Test
//  public void validate() {
//    when(questionService.validate(questionRequest)).thenReturn(questionResponse);
//
//    QuestionResponse response = questionController.validate(questionRequest);
//
//    assertEquals(questionResponse, response);
//  }
//}
