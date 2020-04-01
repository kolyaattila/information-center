package com.information.center.questionservice.controller;

import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.QuestionResponse;
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
@RequestMapping("/question")
public interface QuestionEndpoint {

  @PostMapping("/topic/{topicExternalId}")
  QuestionResponse create(@RequestBody QuestionRequest questionRequest,
      @PathVariable("topicExternalId") String topicExternalId);

  @GetMapping("/questionsByTopic/{topicExternalId}")
  Page<QuestionResponse> findQuestionsByTopicId(
      @PathVariable("topicExternalId") String topicExternalId,
      Pageable pageable);

  @PostMapping("/validate")
  QuestionResponse validate(@RequestBody QuestionRequest questionRequest);

  @PutMapping
  void update(@RequestBody QuestionRequest questionRequest);

  @GetMapping("/{externalId}")
  QuestionResponse findByExternalId(@PathVariable("externalId") String externalId);

  @GetMapping
  Page<QuestionResponse> findAll(Pageable pageable);

  @DeleteMapping("/{externalId}")
  void delete(@PathVariable("externalId") String externalId);
}
