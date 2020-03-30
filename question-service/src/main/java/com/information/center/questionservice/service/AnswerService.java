package com.information.center.questionservice.service;

import com.information.center.questionservice.entity.AnswerEntity;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AnswerService {

  AnswerResponse create(AnswerRequest answerRequest, String questionExternalId);

  void update(AnswerRequest answerRequest);

  AnswerResponse findByExternalId(String externalId);

  Page<AnswerResponse> findAll(Pageable pageable);

  void delete(String externalId);

  AnswerEntity findById(String externalId);
}
