package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.model.response.AnswerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public interface AnswerEndpoint {

  @PostMapping("/question/{questionExternalId}")
  AnswerResponse create(@RequestBody AnswerRequest answerRequest, @PathVariable("questionExternalId") String questionExternalId);

  @PutMapping
  void update(@RequestBody AnswerRequest answerRequest);

  @GetMapping("/{externalId}")
  AnswerResponse findByExternalId(@PathVariable("externalId") String externalId);

  @GetMapping
  Page<AnswerResponse> findAll(Pageable pageable);

  @DeleteMapping("/{externalId}")
  void delete(@PathVariable("externalId") String externalId);
}
