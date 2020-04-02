package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.model.QuestionResponseValidated;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class QuestionValidateService {

    private final AnswerRepository answerRepository;
    private final QuestionConverter questionConverter;
    private static final String TOPIC_ENDPOINT = "/topic/internal/";
    private final CrunchifyTimeDiff crunchifyTimeDiff;
    private final RestTemplate template;


    @Value("${endpoints.topic:http://localhost:8881}")
    private String topicEndpoint;

    public QuestionResponseValidated validate(QuestionRequestValidation questionRequest) {
        questionRequest.getQuestionResponses().forEach(questionResponse -> {
            questionResponse.getAnswers().forEach(answerResponse -> {
                var answer = answerRepository.findByExternalId(answerResponse.getExternalId()).orElseThrow(null);
                answerResponse.setCorrect(answer.isCorrect());
                answerResponse.setReason(answer.getReason());
            });
        });
        var questionValidated = questionConverter.toResponse(questionRequest);
        questionValidated.setEndDate(new Date());

        questionValidated.setTotalTime(crunchifyTimeDiff.dateDifference(questionRequest.getStartDate(), new Date()));
        return questionValidated;
    }

    private String getTopicById(String topicId) {

        ResponseEntity<String> response = template.getForEntity(topicEndpoint + TOPIC_ENDPOINT+topicId, String.class);
        return response.getBody();
    }
}
