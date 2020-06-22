package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.FilterQuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.repository.QuestionRepository;
import com.information.center.quizservice.repository.SchoolRepository;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isBlank;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionServiceImpl implements QuestionService {

    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final SchoolRepository schoolRepository;
    private QuestionConverter questionConverter;


    @Autowired
    public void setQuestionConverter(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }

    @Override
    public QuestionDto create(QuestionRequest questionRequest) {
        QuestionEntity question = questionConverter.toEntity(questionRequest);
        question.setExternalId(getUid());
        question.setSchool(getSchoolEntity(questionRequest.getSchoolExternalId()));
        QuestionEntity entity = getSave(question);

        List<AnswerEntity> answers = questionRequest
                .getAnswers()
                .stream()
                .map(answer -> answerService.saveEntity(answer, entity))
                .collect(Collectors.toList());
        entity.setAnswers(answers);
        return questionConverter.toDtoWithAnswers(entity);
    }

    @Override
    public void update(QuestionRequest questionRequest) {
        QuestionEntity entity = findQuestionEntityByExternalId(questionRequest.getExternalId());
        List<AnswerEntity> updatedEntities = answerService.getUpdatedEntities(questionRequest.getAnswers(), entity);

        entity.setAnswers(updatedEntities);
        entity.setBook(questionRequest.getBook());
        entity.setChapterExternalId(questionRequest.getChapterExternalId());
        entity.setCourseExternalId(questionRequest.getCourseExternalId());
        entity.setName(questionRequest.getName());
        entity.setQuestionDifficulty(questionRequest.getQuestionDifficulty());
        entity.setQuestionNumber(questionRequest.getQuestionNumber());
        entity.setTopicExternalId(questionRequest.getTopicExternalId());
        entity.setVerified(questionRequest.isVerified());
        entity.setSchool(getSchoolEntity(questionRequest.getSchoolExternalId()));

        getSave(entity);
    }

    @Override
    public QuestionDto findByExternalId(String externalId) {
        QuestionEntity question = findQuestionEntityByExternalId(externalId);
        return questionConverter.toDto(question);
    }

    @Override
    public void delete(String externalId) {
        QuestionEntity question = findQuestionEntityByExternalId(externalId);
        questionRepository.delete(question);
    }

    @Override
    public Page<QuestionDto> filterQuestions(FilterQuestionRequest filterRequest) {
        List<QuestionSpecification> questionSpecifications = getQuestionSpecifications(filterRequest);
        Specification<QuestionEntity> specification = getSpecification(questionSpecifications);
        return questionRepository.findAll(specification, PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize()))
                .map(questionConverter::toDtoWithAnswers);
    }

    private Specification<QuestionEntity> getSpecification(List<QuestionSpecification> questionSpecifications) {
        if (questionSpecifications.isEmpty()) {
            return null;
        } else if (questionSpecifications.size() > 1) {
            Specification<QuestionEntity> result = questionSpecifications.get(0);
            for (Specification<QuestionEntity> spec : questionSpecifications) {
                result = Specification.where(result).and(spec);
            }
            return result;
        } else {
            return questionSpecifications.get(0);
        }
    }

    private List<QuestionSpecification> getQuestionSpecifications(FilterQuestionRequest filterRequest) {
        List<QuestionSpecification> questionSpecifications = new ArrayList<>();

        if (!isBlank(filterRequest.getBook()))
            questionSpecifications.add(getQuestionSpecification("book", FilterOperation.EQUALS, filterRequest.getBook()));

        if (!isBlank(filterRequest.getName()))
            questionSpecifications.add(getQuestionSpecification("name", FilterOperation.EQUALS, filterRequest.getName()));

        if (!isBlank(filterRequest.getChapterExternalId()))
            questionSpecifications.add(getQuestionSpecification("topicExternalId", FilterOperation.EQUALS, filterRequest.getChapterExternalId()));

        if (!isBlank(filterRequest.getExternalId()))
            questionSpecifications.add(getQuestionSpecification("externalId", FilterOperation.EQUALS, filterRequest.getExternalId()));

        if (filterRequest.getQuestionDifficulty() != null)
            questionSpecifications.add(getQuestionSpecification("questionDifficulty", FilterOperation.EQUALS, filterRequest.getQuestionDifficulty()));

        if (filterRequest.getQuestionNumber() != 0)
            questionSpecifications.add(getQuestionSpecification("questionNumber", FilterOperation.EQUALS, filterRequest.getQuestionNumber()));

        if (!isBlank(filterRequest.getCourseExternalId()))
            questionSpecifications.add(getQuestionSpecification("courseExternalId", FilterOperation.EQUALS, filterRequest.getCourseExternalId()));

        questionSpecifications.add(getQuestionSpecification("verified", FilterOperation.EQUALS, filterRequest.isVerified()));

        return questionSpecifications;
    }

    private QuestionSpecification getQuestionSpecification(String key, FilterOperation operation, Object value) {
        return new QuestionSpecification(new SearchCriteria(key, operation, value));
    }

    private QuestionEntity findQuestionEntityByExternalId(String externalId) {
        return questionRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceExceptions.NotFoundException("Question not found by id " + externalId));
    }

    private QuestionEntity getSave(QuestionEntity question) {
        try {
            return questionRepository.save(question);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException("Can not insert values" + question.toString());
        }
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (questionRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }

    private SchoolEntity getSchoolEntity(String externalId) {
        return schoolRepository.findByExternalId(externalId).orElse(null);
    }
}
