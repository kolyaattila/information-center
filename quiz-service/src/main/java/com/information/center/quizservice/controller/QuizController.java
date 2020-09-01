package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import com.information.center.quizservice.model.request.QuizValidation;
import com.information.center.quizservice.service.QuestionValidateService;
import com.information.center.quizservice.service.QuizService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import exception.ServiceExceptions.WrongQuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuizController implements QuizEndpoint {

    private final QuizService quizService;
    private final QuestionValidateService questionValidateService;

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

    @Override
    public List<QuizDto> getActiveQuizByCourse(@PathVariable String courseExternalId) {
        return quizService.getActiveQuizzesByCourseId(courseExternalId);
    }

    @Override
    public QuizStartDto getActiveQuiz(@PathVariable String externalId) {
        try {
            return quizService.getActiveQuizById(externalId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public QuizValidation validate(@RequestBody @Valid QuizValidation quizValidation) {
        try {
            return questionValidateService.validate(quizValidation);
        } catch (ServiceExceptions.InconsistentDataException | NotFoundException | InsertFailedException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public QuizValidation getResultQuiz(@PathVariable String externalId){
        try {
            return questionValidateService.getQuizValidation(externalId);
        } catch (NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }
}
