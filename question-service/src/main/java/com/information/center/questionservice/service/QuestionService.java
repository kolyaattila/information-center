package com.information.center.questionservice.service;

import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.model.QuestionListDetails;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.model.response.QuestionResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {

    QuestionResponse create(QuestionRequest questionRequest, String topicExternalId);

    String getTopicNameByExternalId(String topicId);

    void update(QuestionResponse questionResponse);

    QuestionResponse findByExternalId(String externalId);

    QuestionResponsePage findAll(Pageable pageable);

    void delete(String externalId);

    QuestionEntity findById(String externalId);

    QuestionListDetails findQuestionsByTopicId(String topicExternalId, Pageable pageable);
}
