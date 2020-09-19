package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.FilterQuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.service.QuestionService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionController implements QuestionEndpoint {

    private final QuestionService questionService;

    @Override
    public QuestionDto create(@RequestBody @Valid QuestionRequest questionRequest) {
        try {
            return questionService.create(questionRequest);
        } catch (ServiceExceptions.NotFoundException | ServiceExceptions.InsertFailedException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public void update(@RequestBody QuestionRequest questionRequest) {
        try {
            questionService.update(questionRequest);
        } catch (ServiceExceptions.NotFoundException | ServiceExceptions.InsertFailedException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public QuestionDto findByExternalId(@PathVariable("externalId") String externalId) {
        try {
            return questionService.findByExternalId(externalId);
        } catch (ServiceExceptions.NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public void delete(@PathVariable("externalId") String externalId) {
        try {
            questionService.delete(externalId);
        } catch (ServiceExceptions.NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public Page<QuestionDto> filterQuestion(@Valid @RequestBody FilterQuestionRequest filterQuestionRequest) {
        return questionService.filterQuestions(filterQuestionRequest);
    }
}

