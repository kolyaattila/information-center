package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.entity.Question;
import com.information.center.questionservice.model.QuestionListDetails;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.model.response.QuestionResponsePage;
import com.information.center.questionservice.repository.QuestionRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionConverter questionConverter;

    private final AnswerService answerService;

    private final RestTemplate template;

    private static final String ORGANISATION_ENDPOINT = "/topic/internal/";

    @Value("${endpoints.topic:http://localhost:8881}")
    private String equipmentEndpoint;

    String getTopicNameByExternalId(String topicId) {
        return template.getForObject(
                equipmentEndpoint + ORGANISATION_ENDPOINT + topicId,
                String.class);
    }

    public QuestionResponse create(QuestionRequest questionRequest, String topicExternalId) {

        String questionExternalId = UUID.randomUUID().toString();


        Question question = questionConverter.toEntity(questionRequest);
        question.setExternalId(questionExternalId);
        question.setTopicExternalId(topicExternalId);
        question.setAnswers(null);
        var questionFromDb = questionConverter.toResponse(questionRepository.save(question));
        for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
            answerService.create(answerRequest, questionExternalId);
        }


        return questionFromDb;
    }

    public void update(QuestionResponse questionResponse) {
        Question question = findById(questionResponse.getExternalId());
        Question questionPersistent = questionConverter.toEntity(questionResponse, question.getId());
        questionRepository.save(questionPersistent);

    }

    public QuestionResponse findByExternalId(String externalId) {
        QuestionResponse question = questionConverter.toResponse(findById(externalId));
        for (AnswerResponse answer : question.getAnswers()) {
            answer.setCorrect(false);
        }
        return question;

    }

    public QuestionResponsePage findAll(Pageable pageable){
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

    public void delete(String externalId) {
        Question question = findById(externalId);
        questionRepository.delete(question);
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

    public Question findById(String externalId) {
        return questionRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("question", externalId));
    }

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
