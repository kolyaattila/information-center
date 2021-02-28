package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.AnswerConverter;
import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.repository.AnswerRepository;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private AnswerConverter answerConverter;

    @Autowired
    public void setAnswerConverter(AnswerConverter answerConverter) {
        this.answerConverter = answerConverter;
    }

    @Override
    public AnswerEntity saveEntity(AnswerRequest answerRequest, QuestionEntity questionEntity) {
        AnswerEntity answer = getAnswer(answerRequest, questionEntity);
        try {
            return answerRepository.save(answer);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException(e.getMessage());
        }
    }

    @Override
    public List<AnswerEntity> getUpdatedEntities(List<AnswerRequest> answerRequestList, QuestionEntity entity) {
        deleteAnswers(answerRequestList.stream().map(AnswerRequest::getExternalId).collect(Collectors.toList()), entity);
        List<AnswerEntity> updatedEntities = createAnswer(answerRequestList, entity);
        answerRequestList
                .stream()
                .map(this::updateAnswer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(updatedEntities::add);

        return updatedEntities;
    }

    private AnswerEntity getAnswer(AnswerRequest answerRequest, QuestionEntity questionEntity) {
        AnswerEntity answer = answerConverter.toEntity(answerRequest);
        answer.setQuestion(questionEntity);
        answer.setExternalId(getUid());
        return answer;
    }

    private void deleteAnswers(List<String> externalIds, QuestionEntity entity) {
        entity.getAnswers()
                .stream()
                .filter(answer -> !externalIds.contains(answer.getExternalId()))
                .forEach(answerRepository::delete);
    }

    private List<AnswerEntity> createAnswer(List<AnswerRequest> answerRequestList, QuestionEntity entity) {
        List<String> externalIds = entity.getAnswers().stream().map(AnswerEntity::getExternalId).collect(Collectors.toList());
        return answerRequestList.stream()
                .filter(answerRequest -> !externalIds.contains(answerRequest.getExternalId()))
                .map(answerRequest -> this.getAnswer(answerRequest, entity))
                .collect(Collectors.toList());
    }

    private Optional<AnswerEntity> updateAnswer(AnswerRequest answerRequest) {
        return answerRepository.findByExternalId(answerRequest.getExternalId())
                .map(entity -> this.mapAnswerEntity(entity, answerRequest));
    }

    private AnswerEntity mapAnswerEntity(AnswerEntity entity, AnswerRequest request) {
        if (entity == null || request == null)
            return null;

        entity.setName(request.getName());
        entity.setCorrect(request.isCorrect());
        entity.setKey(request.getKey());
        entity.setReason(request.getReason());
        entity.setAnswerType(request.getAnswerType());
        entity.setParseText(request.getParseText());
        return entity;
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (answerRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }
}
