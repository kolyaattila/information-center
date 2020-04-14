package com.information.center.questionservice.service;

import com.information.center.questionservice.client.TopicServiceClient;
import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.model.QuestionResponseValidated;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class QuestionValidateServiceImpl implements QuestionValidateService {

    private final AnswerRepository answerRepository;
    private final QuestionConverter questionConverter;
    private final CrunchifyTimeDiff crunchifyTimeDiff;
    private final TopicServiceClient topicServiceClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionValidateServiceImpl.class);

    @Override
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

    @Override
    public String getTopicById(String topicId) {

        String response = topicServiceClient.getTopicNameByTopicId(topicId);
        if (response.equals(""))
            LOGGER.warn("Error while getting response from topic-service");
        return response;

    }
}
