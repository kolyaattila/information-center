package com.information.center.questionservice.controller;

import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/topic/{topicExternalId}")
    public QuestionResponse create(@RequestBody QuestionRequest questionRequest, @PathVariable("topicExternalId") String topicExternalId) {

        return questionService.create(questionRequest, topicExternalId);
    }

    @GetMapping("/questionsByTopic/{topicExternalId}")
    public Page<QuestionResponse> findQuestionsByTopicId(@PathVariable("topicExternalId") String topicExternalId,
                                                         Pageable pageable) {
        return questionService.findQuestionsByTopicId(topicExternalId, pageable);
    }

    @PostMapping("/validate")
    public QuestionResponse validate(@RequestBody QuestionRequest questionRequest) {

        return questionService.validate(questionRequest);
    }

    @PutMapping
    public void update(@RequestBody QuestionResponse questionResponse) {
        questionService.update(questionResponse);
    }

    @GetMapping("/{externalId}")
    public QuestionResponse findByExternalId(@PathVariable("externalId") String externalId) {
        return questionService.findByExternalId(externalId);
    }

    @GetMapping
    public Page<QuestionResponse> findAll(Pageable pageable) {
        return questionService.findAll(pageable);
    }

    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId) {

        questionService.delete(externalId);
    }
}

