package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.entity.*;
import com.information.center.quizservice.model.AnswerDto;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.QuizValidation;
import com.information.center.quizservice.repository.AnsweredQuestionRepository;
import com.information.center.quizservice.repository.ExternalIdRepository;
import com.information.center.quizservice.repository.QuizRepository;
import com.information.center.quizservice.repository.SolvedQuizRepository;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionValidateServiceImpl implements QuestionValidateService {

    private final QuizRepository quizRepository;
    private final AnsweredQuestionRepository answeredQuestionRepository;
    private final SolvedQuizRepository solvedQuizRepository;
    private QuestionConverter questionConverter;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionValidateServiceImpl.class);

    @Override
    public QuizValidation getQuizValidation(String externalId) {
        SolvedQuizEntity solvedQuizEntity = solvedQuizRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceExceptions.InconsistentDataException("Not found any quiz resolved with id: " + externalId));


        QuizValidation quizValidation = new QuizValidation();

        quizValidation.setPassed(solvedQuizEntity.isPassed());
        quizValidation.setNote(solvedQuizEntity.getNote());
        quizValidation.setExternalId(solvedQuizEntity.getExternalId());
        quizValidation.setUserExternalId(solvedQuizEntity.getUserExternalId());

        List<QuestionDto> collect = solvedQuizEntity.getAnsweredQuestionEntities()
                .stream()
                .distinct()
                .map(this::mapToQuestionDto)
                .collect(Collectors.toList());

        quizValidation.setQuestions(collect);

        return quizValidation;
    }

    private QuestionDto mapToQuestionDto(AnsweredQuestionEntity answeredQuestionEntity) {
        QuestionDto questionDto = questionConverter.toDtoWithAnswers(answeredQuestionEntity.getQuestionEntity());
        questionDto.setStatus(answeredQuestionEntity.isStatus());

        List<String> selectedAnswers = answeredQuestionEntity.getAnswerEntities()
                .stream()
                .map(AnswerEntity::getExternalId)
                .collect(Collectors.toList());

        questionDto.getAnswers()
                .forEach(dto -> {
                    if (selectedAnswers.contains(dto.getExternalId())) {
                        dto.setSelected(true);
                    }
                });
        return questionDto;
    }


    @Override
    public QuizValidation validate(QuizValidation quizValidation) {
        QuizEntity activeQuiz = findByExternalId(quizValidation.getExternalId());

        SolvedQuizEntity solvedQuizEntity = new SolvedQuizEntity();
        solvedQuizEntity.setUserExternalId(quizValidation.getUserExternalId());
        solvedQuizEntity.setQuiz(activeQuiz);
        solvedQuizEntity.setExternalId(getUid(solvedQuizRepository));

        List<AnsweredQuestionEntity> collect = activeQuiz.getQuestions()
                .stream()
                .distinct()
                .map(entity -> mapToAnsweredQuestions(entity, quizValidation, solvedQuizEntity))
                .collect(Collectors.toList());

        solvedQuizEntity.setAnsweredQuestionEntities(collect);
        boolean passedQuiz = solvedQuizEntity.getNote() > (activeQuiz.getQuestions().size() / 2.0);
        solvedQuizEntity.setPassed(passedQuiz);

        try {
            solvedQuizRepository.save(solvedQuizEntity);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException(e.getMessage());
        }

        quizValidation.setExternalId(solvedQuizEntity.getExternalId());
        quizValidation.setNote(solvedQuizEntity.getNote());
        quizValidation.setPassed(solvedQuizEntity.isPassed());
        return quizValidation;
    }

    private AnsweredQuestionEntity mapToAnsweredQuestions(QuestionEntity entity, QuizValidation quizValidation, SolvedQuizEntity solvedQuizEntity) {
        QuestionDto questionDto = quizValidation.getQuestions()
                .stream()
                .filter(question -> question.getExternalId().equals(entity.getExternalId()))
                .findFirst()
                .orElseThrow(() -> new ServiceExceptions.InconsistentDataException("Some question was not sent for validation" + entity.getExternalId()));

        double points = validateQuestion(entity, questionDto);
        solvedQuizEntity.setNote(solvedQuizEntity.getNote() + points);
        List<String> selectedAnswers = questionDto.getAnswers()
                .stream()
                .filter(AnswerDto::isSelected)
                .map(AnswerDto::getExternalId)
                .collect(Collectors.toList());

        List<AnswerEntity> answerEntities = entity.getAnswers()
                .stream()
                .filter(answerEntity -> selectedAnswers.contains(answerEntity.getExternalId()))
                .collect(Collectors.toList());

        AnsweredQuestionEntity answeredQuestionEntity = new AnsweredQuestionEntity();
        answeredQuestionEntity.setQuestionEntity(entity);
        answeredQuestionEntity.setExternalId(getUid(answeredQuestionRepository));
        answeredQuestionEntity.setAnswerEntities(answerEntities);
        answeredQuestionEntity.setSolvedQuiz(solvedQuizEntity);

        if (points > 0) {
            answeredQuestionEntity.setStatus(true);
        }

        return answeredQuestionEntity;
    }

    private String getUid(ExternalIdRepository repository) {
        UUID uuid = UUID.randomUUID();
        if (repository.existsByExternalId(uuid.toString())) {
            return this.getUid(repository);
        }
        return uuid.toString();
    }

    private QuizEntity findByExternalId(String externalId) {
        return quizRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceExceptions.NotFoundException("Quiz not found by id " + externalId));
    }

    private int getNumOfCorrectAnswers(QuestionEntity entity) {
        return (int) entity.getAnswers()
                .stream()
                .filter(AnswerEntity::isCorrect)
                .count();
    }

    private double validateQuestion(QuestionEntity entity, QuestionDto dto) {
        try {
            int numOfCorrectAnswers = getNumOfCorrectAnswers(entity);
            double numOfCorrectAnswersSelected = dto.getAnswers()
                    .stream()
                    .map(answerDto -> getNumberOfCorrectAnswersSelected(entity, answerDto))
                    .mapToInt(Integer::intValue)
                    .asDoubleStream()
                    .sum();

            if (numOfCorrectAnswersSelected != 0) {
                dto.setStatus(true);
            }

            return numOfCorrectAnswersSelected / numOfCorrectAnswers;

        } catch (ServiceExceptions.InvalidAnswerException e) {
            return 0;
        }
    }

    private Integer getNumberOfCorrectAnswersSelected(QuestionEntity entity, AnswerDto answerDto) {
        return entity.getAnswers().stream()
                .filter(answerEntity -> answerEntity.getExternalId().equals(answerDto.getExternalId()))
                .findFirst()
                .map(matchAnswer -> this.validateAnswer(matchAnswer, answerDto))
                .orElseThrow(() -> new ServiceExceptions.InconsistentDataException("Some answer was not sent for validation" + entity.getExternalId()));
    }

    private int validateAnswer(AnswerEntity entity, AnswerDto dto) {
        dto.setCorrect(entity.isCorrect());
        if (entity.isCorrect() == dto.isSelected() && entity.isCorrect())
            return 1;
        else if (!entity.isCorrect() && entity.isCorrect() != dto.isSelected())
            throw new ServiceExceptions.InvalidAnswerException();
        else return 0;
    }

    @Autowired
    public void setQuestionConverter(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }
}
