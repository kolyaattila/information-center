package com.information.center.questionservice.controller;

import com.information.center.questionservice.entity.Answer;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/question/{questionExternalId}")
    public AnswerResponse create(@RequestBody AnswerRequest answerRequest,@PathVariable("questionExternalId") String questionExternalId){

        return answerService.create(answerRequest,questionExternalId);
    }

    @PutMapping
    public void update(@RequestBody AnswerResponse answerResponse){
        answerService.update(answerResponse);
    }

    @GetMapping("/{externalId}")
    public AnswerResponse findByExternalId(@PathVariable("externalId") String externalId ){
        return answerService.findByExternalId(externalId);
    }

    @GetMapping
    public Page<AnswerResponse> findAll(Pageable pageable){
        return answerService.findAll(pageable);
    }

    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId){

        answerService.delete(externalId);
    }
}
