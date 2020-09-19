package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.converter.QuizConverter;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.entity.QuizEntity;
import com.information.center.quizservice.entity.QuizType;
import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import com.information.center.quizservice.repository.QuestionRepository;
import com.information.center.quizservice.repository.QuizRepository;
import com.information.center.quizservice.repository.SchoolRepository;
import exception.ServiceExceptions;
import exception.ServiceExceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final SchoolRepository schoolRepository;
    private QuizConverter quizConverter;
    private QuestionConverter questionConverter;

    @Autowired
    public void setQuizConverter(QuizConverter quizConverter) {
        this.quizConverter = quizConverter;
    }

    @Autowired
    public void setQuestionConverter(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }

    @Override
    public void createQuiz(QuizRequest quizRequest) {
        if (!isTypeChapterOrCourse(quizRequest)) {
            throw new ServiceExceptions.WrongQuizType("You can not create quiz of other type then course and chapter");
        }

        QuizEntity quizEntity = quizConverter.toEntity(quizRequest);
        quizEntity.setSchool(getSchool(quizRequest.getSchoolExternalId()));
        quizEntity.setQuestions(getQuestions(quizRequest));
        quizEntity.setExternalId(getUid());
        try {
            quizRepository.save(quizEntity);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException("Can't save the quiz " + quizEntity.toString());
        }
    }

    @Override
    public void updateQuiz(QuizRequest quizRequest) {
        if (!isTypeChapterOrCourse(quizRequest)) {
            throw new ServiceExceptions.WrongQuizType("You can not change quiz type to something else then course and chapter");
        }

        QuizEntity entity = findByExternalId(quizRequest.getExternalId());
        copyValues(entity, quizRequest);
        try {
            quizRepository.save(entity);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException("Can't save the quiz " + entity.toString());
        }
    }

    private void copyValues(QuizEntity entity, QuizRequest quizRequest) {
        entity.setSchool(getSchool(quizRequest.getSchoolExternalId()));
        entity.setQuestions(getQuestions(quizRequest));
        entity.setEnable(quizRequest.getEnable());
        entity.setChapterExternalId(quizRequest.getChapterExternalId());
        entity.setType(quizRequest.getType());
        entity.setCourseExternalId(quizRequest.getCourseExternalId());
    }

    @Override
    public List<QuizDto> getAll() {
        return quizRepository.findAll().stream()
                .map(quizConverter::toDtoWithQuestions)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizDto> getActiveQuizzesByCourseId(String courseExternalId) {
        return quizRepository.findAllByCourseExternalIdAndEnable(courseExternalId, true)
                .stream()
                .map(quizConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizStartDto getActiveQuizById(String externalId) {
        QuizEntity quizEntity = quizRepository.findByExternalIdAndEnable(externalId, true)
                .orElseThrow(() -> new NotFoundException("Quiz not found by id " + externalId));

        QuizStartDto quizStartDto = quizConverter.toQuizStartDto(quizEntity);
        List<QuestionDto> questionDtos = quizEntity.getQuestions().stream()
                .distinct()
                .map(questionConverter::toDtoWithAnswersWithoutCorrectValue)
                .collect(Collectors.toList());

        quizStartDto.setQuestions(questionDtos);
        return quizStartDto;
    }

    private SchoolEntity getSchool(String externalId) {
        return schoolRepository.findByExternalId(externalId)
                .orElseThrow(() -> new NotFoundException("School not found by id " + externalId));
    }

    private List<QuestionEntity> getQuestions(QuizRequest quizRequest) {
        return quizRequest.getQuestionIds().stream()
                .map(questionRepository::findByExternalId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (quizRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }

    private QuizEntity findByExternalId(String externalId) {
        return quizRepository.findByExternalId(externalId)
                .orElseThrow(() -> new NotFoundException("Quiz not found by id " + externalId));
    }

    private boolean isTypeChapterOrCourse(QuizRequest request) {
        return request.getType() != null && (request.getType() == QuizType.CHAPTER || request.getType() == QuizType.COURSE);
    }
}
