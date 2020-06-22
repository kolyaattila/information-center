package com.information.center.quizservice.service;

import com.information.center.quizservice.model.request.QuizValidation;
import org.springframework.stereotype.Service;

@Service
public interface QuestionValidateService {

    QuizValidation validate(QuizValidation quizValidation);

     QuizValidation getQuizValidation(String externalId);
}
