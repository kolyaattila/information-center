package com.information.center.quizservice.service;

import com.information.center.quizservice.client.CourseServiceClient;
import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.model.QuestionResponseValidated;
import com.information.center.quizservice.model.request.QuestionRequestValidation;
import com.information.center.quizservice.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class QuestionValidateServiceImpl implements QuestionValidateService {

    private final AnswerRepository answerRepository;
    private final QuestionConverter questionConverter;
    private final CrunchifyTimeDiff crunchifyTimeDiff;
    private final CourseServiceClient courseServiceClient;
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

        String response = courseServiceClient.getTopicNameByTopicId(topicId);
        if (response.equals(""))
            LOGGER.warn("Error while getting response from course-service");
        return response;

    }
}
