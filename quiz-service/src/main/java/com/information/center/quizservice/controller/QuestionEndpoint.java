package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.QuestionListDetails;
import com.information.center.quizservice.model.QuestionResponseValidated;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequestValidation;
import com.information.center.quizservice.model.response.QuestionResponse;
import com.information.center.quizservice.model.response.QuestionResponsePage;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public interface QuestionEndpoint {
    @PostMapping("/topic/{topicExternalId}")
    QuestionResponse create(@RequestBody QuestionRequest questionRequest,
                            @PathVariable("topicExternalId") String topicExternalId);

    @GetMapping("/questionsByTopic/{topicExternalId}")
    QuestionListDetails findQuestionsByTopicId(@PathVariable("topicExternalId") String topicExternalId,
                                               Pageable pageable);

    @PostMapping("/validate")
    QuestionResponseValidated validate(@RequestBody QuestionRequestValidation questionRequestValidation);

    @PutMapping
    void update(@RequestBody QuestionRequest questionRequest);

    @GetMapping("/{externalId}")
    QuestionResponse findByExternalId(@PathVariable("externalId") String externalId);

    @GetMapping
    QuestionResponsePage findAll(Pageable pageable);

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId);
}
