package com.information.center.questionservice.service;

import com.information.center.questionservice.model.QuestionResponseValidated;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import org.springframework.stereotype.Service;

@Service
public interface QuestionValidateService {

    QuestionResponseValidated validate(QuestionRequestValidation questionRequest);

    String getTopicById(String topicId);
}
