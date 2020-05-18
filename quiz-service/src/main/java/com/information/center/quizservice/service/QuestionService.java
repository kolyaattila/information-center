package com.information.center.quizservice.service;

import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.FilterQuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {

    QuestionDto create(QuestionRequest questionRequest);

    void update(QuestionRequest questionRequest);

    QuestionDto findByExternalId(String externalId);

    void delete(String externalId);

    Page<QuestionDto> filterQuestions(FilterQuestionRequest filterRequest);
}
