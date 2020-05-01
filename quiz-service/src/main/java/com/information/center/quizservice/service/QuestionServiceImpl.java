package com.information.center.quizservice.service;

import com.information.center.quizservice.client.TopicServiceClient;
import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.model.QuestionListDetails;
import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.model.response.AnswerResponse;
import com.information.center.quizservice.model.response.QuestionResponse;
import com.information.center.quizservice.model.response.QuestionResponsePage;
import com.information.center.quizservice.repository.QuestionRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final TopicServiceClient topicServiceClient;

    private final QuestionConverter questionConverter;

    private final AnswerService answerService;

    @Override
    public String getTopicNameByExternalId(String topicId) {
        return topicServiceClient.getTopicNameByTopicId(topicId);
    }

    @Override
    public QuestionResponse create(QuestionRequest questionRequest, String topicExternalId) {

        String questionExternalId = UUID.randomUUID().toString();


        QuestionEntity question = questionConverter.toEntity(questionRequest);
        question.setExternalId(questionExternalId);
        question.setTopicExternalId(topicExternalId);
        question.setAnswers(null);
        var questionFromDb = questionConverter.toResponse(questionRepository.save(question));
        for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
            answerService.create(answerRequest, questionExternalId);
        }


        return questionFromDb;
    }

    @Override
    public void update(QuestionRequest questionRequest) {
        QuestionEntity question = findById(questionRequest.getExternalId());
        QuestionEntity questionPersistent = questionConverter.toEntity(questionRequest, question.getId());
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
    public QuestionResponsePage findAll(Pageable pageable) {
        Page<QuestionResponse> questionPage = questionRepository.findAll(pageable)
                .map(question -> {
                    QuestionResponse response = questionConverter.toResponse(question);
                    for (AnswerResponse answer : response.getAnswers()) {
                        answer.setCorrect(false);
                    }
                    return response;
                });
        var questionResponsePage = new QuestionResponsePage();
        questionResponsePage.setQuestionResponses(questionPage);
        questionResponsePage.setStartDate(new Date());
        return questionResponsePage;
    }

    @Override
    public void delete(String externalId) {
        QuestionEntity question = findById(externalId);
        questionRepository.delete(question);
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

    @Override
    public QuestionEntity findById(String externalId) {
        return questionRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("question", externalId));
    }

    @Override
    public QuestionListDetails findQuestionsByTopicId(String topicExternalId, Pageable pageable) {
        var questionDetails = new QuestionListDetails();
        Page<QuestionResponse> questions = questionRepository.findQuestionsByTopicExternalId(topicExternalId, pageable)
                .map(question -> {

                    QuestionResponse response = questionConverter.toResponse(question);
                    for (AnswerResponse answer : response.getAnswers()) {
                        answer.setCorrect(false);
                    }
                    return response;
                });
        questionDetails.setQuestionResponseList(questions);
        questionDetails.setStartDate(new Date());

        questionDetails.setTopicName(getTopicNameByExternalId(topicExternalId));
        return questionDetails;

    }
}
