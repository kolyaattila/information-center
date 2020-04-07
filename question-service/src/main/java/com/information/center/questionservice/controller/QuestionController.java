package com.information.center.questionservice.controller;

import com.information.center.questionservice.model.QuestionListDetails;
import com.information.center.questionservice.model.QuestionResponseValidated;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.model.response.QuestionResponsePage;
import com.information.center.questionservice.service.QuestionService;
import com.information.center.questionservice.service.QuestionValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionController implements QuestionEndpoint {

    private final QuestionService questionService;

    private final QuestionValidateService questionValidateService;

    @Override
    @PostMapping("/topic/{topicExternalId}")
    public QuestionResponse create(@RequestBody QuestionRequest questionRequest, @PathVariable("topicExternalId") String topicExternalId) {

        return questionService.create(questionRequest, topicExternalId);
    }

    @Override
    @GetMapping("/questionsByTopic/{topicExternalId}")
    public QuestionListDetails findQuestionsByTopicId(@PathVariable("topicExternalId") String topicExternalId,
                                                      Pageable pageable) {
        return questionService.findQuestionsByTopicId(topicExternalId, pageable);
    }

    @Override
    @PutMapping
    public void update(@RequestBody QuestionRequest questionRequest) {
        questionService.update(questionRequest);
    }

    @Override
    @GetMapping("/{externalId}")
    public QuestionResponse findByExternalId(@PathVariable("externalId") String externalId) {
        return questionService.findByExternalId(externalId);
    }

    @GetMapping
    public QuestionResponsePage findAll(Pageable pageable) {
        return questionService.findAll(pageable);
    }

    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId) {

        questionService.delete(externalId);
    }

    @PostMapping("/validate")
    public QuestionResponseValidated validate(@RequestBody QuestionRequestValidation questionRequestValidation) {
        return questionValidateService.validate(questionRequestValidation);
    }
}

