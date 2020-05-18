package com.information.center.quizservice.service;

import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.request.AnswerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    AnswerEntity saveEntity(AnswerRequest answerRequest, QuestionEntity questionEntity);

    List<AnswerEntity> getUpdatedEntities(List<AnswerRequest> answerRequest, QuestionEntity entity);
}
