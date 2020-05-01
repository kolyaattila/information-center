package com.information.center.quizservice.service;

import com.information.center.quizservice.model.QuestionResponseValidated;
import com.information.center.quizservice.model.request.QuestionRequestValidation;
import org.springframework.stereotype.Service;

@Service
public interface QuestionValidateService {

    QuestionResponseValidated validate(QuestionRequestValidation questionRequest);

    String getTopicById(String topicId);
}
