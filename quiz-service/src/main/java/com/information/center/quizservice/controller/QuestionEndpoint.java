package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.FilterQuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/question")
public interface QuestionEndpoint {
    @PostMapping
    QuestionDto create(@RequestBody @Valid QuestionRequest questionRequest);

    @PutMapping
    void update(@RequestBody @Valid QuestionRequest questionRequest);

    @GetMapping("/{externalId}")
    QuestionDto findByExternalId(@PathVariable("externalId") String externalId);

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId);

    @PostMapping("/filter")
    Page<QuestionDto> filterQuestion(@RequestBody @Valid FilterQuestionRequest filterQuestionRequest);
}
