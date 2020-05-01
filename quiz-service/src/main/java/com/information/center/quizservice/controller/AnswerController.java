package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.model.response.AnswerResponse;
import com.information.center.quizservice.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerController implements AnswerEndpoint {

    private final AnswerService answerService;

    @Override
    public AnswerResponse create(@RequestBody AnswerRequest answerRequest, @PathVariable("questionExternalId") String questionExternalId) {

        return answerService.create(answerRequest, questionExternalId);
    }

    @Override
    public void update(@RequestBody AnswerRequest answerRequest) {
        answerService.update(answerRequest);
    }

    @Override
    public AnswerResponse findByExternalId(@PathVariable("externalId") String externalId) {
        return answerService.findByExternalId(externalId);
    }

    @Override
    public Page<AnswerResponse> findAll(Pageable pageable) {
        return answerService.findAll(pageable);
    }

    @Override
    public void delete(@PathVariable("externalId") String externalId) {

        answerService.delete(externalId);
    }
}
