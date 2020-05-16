package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.request.QuizRequest;
import com.information.center.quizservice.service.QuizService;
import exception.RestExceptions;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import exception.ServiceExceptions.WrongQuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuizController implements QuizEndpoint {

    private final QuizService quizService;

    @Override
    public ResponseEntity<String> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        try {
            quizService.createQuiz(quizRequest);
        } catch (InsertFailedException | NotFoundException | WrongQuizType e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        try {
            quizService.updateQuiz(quizRequest);
        } catch (InsertFailedException | NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
