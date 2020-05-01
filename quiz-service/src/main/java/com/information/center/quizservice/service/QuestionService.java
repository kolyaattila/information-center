package com.information.center.quizservice.service;

import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.QuestionListDetails;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.model.response.QuestionResponse;
import com.information.center.quizservice.model.response.QuestionResponsePage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {

    QuestionResponse create(QuestionRequest questionRequest, String topicExternalId);

    String getTopicNameByExternalId(String topicId);

    void update(QuestionRequest questionRequest);

    QuestionResponse findByExternalId(String externalId);

    QuestionResponsePage findAll(Pageable pageable);

    void delete(String externalId);

    QuestionEntity findById(String externalId);

    QuestionListDetails findQuestionsByTopicId(String topicExternalId, Pageable pageable);
}
