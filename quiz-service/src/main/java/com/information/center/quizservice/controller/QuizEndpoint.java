package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public interface QuizEndpoint {

    @PostMapping
    ResponseEntity<String> createQuiz(@Valid @RequestBody QuizRequest quizRequest);

    @PutMapping
    ResponseEntity<String> updateQuiz(@Valid @RequestBody QuizRequest quizRequest);

    @GetMapping("active-quiz/course/{courseExternalId}")
    List<QuizDto> getActiveQuizByCourse(@PathVariable String courseExternalId);

    @GetMapping("active-quiz/{externalId}")
    QuizStartDto getActiveQuiz(@PathVariable String externalId);
}
