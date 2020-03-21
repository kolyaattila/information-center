package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.repository.QuestionRepository;
import exception.MicroserviceException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionConverter questionConverter;

  @Override
  public QuestionResponse create(QuestionRequest questionRequest, String topicExternalId) {

    String questionExternalId = UUID.randomUUID().toString();

    for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
      answerRequest.setQuestionExternalId(questionExternalId);
      answerRequest.setExternalId(UUID.randomUUID().toString());
    }
    QuestionEntity question = questionConverter.toEntity(questionRequest);
    question.setExternalId(questionExternalId);
    question.setTopicExternalId(topicExternalId);

    return questionConverter.toResponse(questionRepository.save(question));
  }

  @Override
  public void update(QuestionRequest questionRequest) {
    QuestionEntity question = findById(questionRequest.getExternalId());
    QuestionEntity questionPersistent = questionConverter
        .toEntity(questionRequest, question.getId());
    questionRepository.save(questionPersistent);

  }

  @Override
  public QuestionResponse findByExternalId(String externalId) {
    QuestionResponse question = questionConverter.toResponse(findById(externalId));
    for (AnswerResponse answer : question.getAnswers()) {
      answer.setCorrect(false);
    }
    return question;

  }

  @Override
  public Page<QuestionResponse> findAll(Pageable pageable) {
    return questionRepository.findAll(pageable)
        .map(question -> {
          QuestionResponse response = questionConverter.toResponse(question);
          for (AnswerResponse answer : response.getAnswers()) {
            answer.setCorrect(false);
          }
          return response;
        });
  }

  @Override
  public void delete(String externalId) {
    QuestionEntity question = findById(externalId);
    questionRepository.delete(question);
  }

  @Override
  public QuestionEntity findById(String externalId) {
    return questionRepository.findByExternalId(externalId)
        .orElseThrow(throwNotFoundItem("question", externalId));
  }

  @Override
  public QuestionResponse validate(QuestionRequest questionRequest) {
    List<AnswerRequest> answerToBeSend = questionRequest.getAnswers();
    List<AnswerRequest> answerRequests;

    QuestionRequest question = questionRepository.findByExternalId(questionRequest.getExternalId())
        .map(questionConverter::toRequest)
        .orElseThrow(throwNotFoundItem("question", questionRequest.getExternalId()));

    answerRequests = question.getAnswers();
    if (answerToBeSend.size() == answerRequests.size()) {
      for (int i = 0; i < answerToBeSend.size(); i++) {
        answerToBeSend.get(i).setCorrect(answerRequests.get(i).isCorrect());
      }
    }
    questionRequest.setAnswers(answerToBeSend);
    return questionConverter.toResponse(questionRequest);
  }

  @Override
  public Page<QuestionResponse> findQuestionsByTopicId(String topicExternalId, Pageable pageable) {
    return questionRepository.findQuestionsByTopicExternalId(topicExternalId, pageable)
        .map(question -> {

          QuestionResponse response = questionConverter.toResponse(question);
          for (AnswerResponse answer : response.getAnswers()) {
            answer.setCorrect(false);
          }
          return response;
        });
  }

  private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
    return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
        "Cannot find " + item + " by id " + itemId);
  }
}
