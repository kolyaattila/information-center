package com.information.center.questionservice.service;

import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {

  QuestionResponse create(QuestionRequest questionRequest, String topicExternalId);

  void update(QuestionRequest questionRequest);

  QuestionResponse findByExternalId(String externalId);

  Page<QuestionResponse> findAll(Pageable pageable);

  void delete(String externalId);

  QuestionEntity findById(String externalId);

  QuestionResponse validate(QuestionRequest questionRequest);

  Page<QuestionResponse> findQuestionsByTopicId(String topicExternalId, Pageable pageable);
}
