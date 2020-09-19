package com.information.center.quizservice.service;

import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    void createQuiz(QuizRequest quizRequest);

    void updateQuiz(QuizRequest quizRequest);

    List<QuizDto> getAll();

    List<QuizDto> getActiveQuizzesByCourseId(String courseExternalId);

    QuizStartDto getActiveQuizById(String externalId);
}
