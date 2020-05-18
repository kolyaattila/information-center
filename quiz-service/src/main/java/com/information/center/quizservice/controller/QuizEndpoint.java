package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.request.QuizRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public interface QuizEndpoint {

    @PostMapping
    ResponseEntity<String> createQuiz(@Valid @RequestBody QuizRequest quizRequest);

    @PutMapping
    ResponseEntity<String> updateQuiz(@Valid @RequestBody QuizRequest quizRequest);

}
