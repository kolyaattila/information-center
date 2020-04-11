package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.AnswerConverter;
import com.information.center.questionservice.entity.AnswerEntity;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.repository.AnswerRepository;
import com.information.center.questionservice.repository.QuestionRepository;
import exception.MicroserviceException;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;
  private final AnswerConverter answerConverter;

  @Override
  public AnswerResponse create(AnswerRequest answerRequest, String questionExternalId) {

    AnswerEntity answer = answerConverter.toEntity(answerRequest);
    var question = questionRepository.findByExternalId(questionExternalId).orElseThrow(throwNotFoundItem("question",questionExternalId));
    answer.setQuestion(question);
    answer.setExternalId(UUID.randomUUID().toString());
    answer.setExternalId(questionExternalId);
    return answerConverter.toResponse(answerRepository.save(answer));
  }

  @Override
  public void update(AnswerRequest answerRequest) {
    AnswerEntity answer = findById(answerRequest.getExternalId());
    AnswerEntity answerPersistent = answerConverter.toEntity(answerRequest, answer.getId());
    answerRepository.save(answerPersistent);
  }

  @Override
  public AnswerResponse findByExternalId(String externalId) {
    return answerConverter.toResponse(findById(externalId));
  }

  public Page<AnswerResponse> findAll(Pageable pageable) {
    return answerRepository.findAll(pageable)
        .map(answerConverter::toResponse);
  }

  @Override
  public void delete(String externalId) {
    AnswerEntity answer = findById(externalId);
    answerRepository.delete(answer);
  }

  @Override
  public AnswerEntity findById(String externalId) {
    return answerRepository.findByExternalId(externalId)
        .orElseThrow(throwNotFoundItem("answer", externalId));
  }

  private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
    return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
        "Cannot find " + item + " by id " + itemId);
  }
}
