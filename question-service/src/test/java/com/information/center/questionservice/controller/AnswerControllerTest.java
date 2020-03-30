package com.information.center.questionservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.service.AnswerService;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@RunWith(MockitoJUnitRunner.class)
public class AnswerControllerTest {

  @InjectMocks
  private AnswerController answerController;
  @Mock
  private AnswerService answerService;
  private AnswerRequest answerRequest;
  private AnswerResponse answerResponse;

  @Before
  public void setUp() {
    answerRequest = AnswerRequest.builder()
        .name("name").build();

    answerResponse = AnswerResponse.builder().build();

  }

  @Test
  public void create_expectResponse() {
    when(answerService.create(answerRequest, "questionExternalId")).thenReturn(answerResponse);

    AnswerResponse response = answerController
        .create(answerRequest, "questionExternalId");

    assertEquals(answerResponse, response);
  }


  @Test
  public void findByExternalId_expectResponse() {
    when(answerService.findByExternalId("externalId")).thenReturn(answerResponse);

    AnswerResponse response = answerController.findByExternalId("externalId");

    assertEquals(answerResponse, response);
  }

  @Test
  public void delete_expectResponse() {
    answerController.delete("externalId");

    verify(answerService).delete("externalId");
  }

  @Test
  public void findAll_expectResponse() {
    PageRequest pageRequest = new PageRequest(1, 1);
    when(answerService.findAll(pageRequest))
        .thenReturn(new PageImpl<>(Collections.singletonList(answerResponse)));

    Page<AnswerResponse> response = answerController.findAll(pageRequest);

    assertEquals(1, response.getTotalElements());
  }

  @Test
  public void update_expectResponse() {
    answerController.update(answerRequest);

    verify(answerService).update(answerRequest);
  }
}
