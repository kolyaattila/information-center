package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.AnswerConverter;
import com.information.center.questionservice.entity.Answer;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.repository.AnswerRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final AnswerConverter answerConverter;

    public AnswerResponse create(AnswerRequest answerRequest, String questionExternalId) {

        Answer answer = answerConverter.toEntity(answerRequest);
        answer.setExternalId(UUID.randomUUID().toString());
        answer.setExternalId(questionExternalId);
        return answerConverter.toResponse(answerRepository.save(answer));
    }

    public void update(AnswerResponse answerResponse) {
        Answer answer = findById(answerResponse.getExternalId());
        Answer answerPersistent = answerConverter.toEntity(answerResponse, answer.getId());
        answerRepository.save(answerPersistent);
    }

    public AnswerResponse findByExternalId(String externalId) {
        return answerConverter.toResponse(findById(externalId));
    }

    public Page<AnswerResponse> findAll(Pageable pageable) {
        return answerRepository.findAll(pageable)
                .map(answerConverter::toResponse);
    }

    public void delete(String externalId) {
        Answer answer = findById(externalId);
        answerRepository.delete(answer);
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

    public Answer findById(String externalId) {
        return answerRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("answer", externalId));
    }
}
